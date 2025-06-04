package com.meuprojeto.alertaapagao.service;

import com.meuprojeto.alertaapagao.model.ClimaInfo;
import com.meuprojeto.alertaapagao.model.Weather;
import com.meuprojeto.alertaapagao.model.Wind;
import com.meuprojeto.alertaapagao.model.Rain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço responsável por analisar os dados climáticos e identificar riscos
 * potenciais que podem levar a apagões.
 */
public class RiscoAnaliseService {

    private static final double LIMIAR_VENTO_FORTE_KMH = 50.0; // km/h (Ventos fortes)
    private static final double LIMIAR_RAJADA_FORTE_KMH = 70.0; // km/h (Rajadas perigosas)
    private static final double LIMIAR_CHUVA_INTENSA_MMH = 10.0; // mm/h (Chuva forte)
    private static final double LIMIAR_CHUVA_MUITO_INTENSA_MMH = 25.0; // mm/h (Chuva muito forte / torrencial)

    /**
     * Analisa os dados climáticos de um local para identificar riscos de apagão.
     *
     * @param climaInfo Dados climáticos obtidos da API OpenWeatherMap.
     * @return Uma lista de strings descrevendo os riscos encontrados. Retorna uma lista vazia se nenhum risco for identificado.
     */
    public List<String> analisarRisco(ClimaInfo climaInfo) {
        List<String> riscos = new ArrayList<>();

        if (climaInfo == null) {
            System.err.println("Aviso: Recebido ClimaInfo nulo para análise de risco.");
            return riscos; // Retorna lista vazia se não há dados
        }

        // 1. Análise baseada nas Condições Climáticas Gerais (Weather ID)
        // Referência: https://openweathermap.org/weather-conditions
        if (climaInfo.getWeather() != null) {
            for (Weather weather : climaInfo.getWeather()) {
                int conditionId = weather.getId();
                String description = weather.getDescription();

                // 2xx: Tempestade Elétrica
                if (conditionId >= 200 && conditionId < 300) {
                    riscos.add("Risco Alto: Tempestade Elétrica detectada (" + description + "). Possibilidade de raios e ventos fortes.");
                }
                // 5xx: Chuva - Foco em intensidades altas
                else if (conditionId == 502) { // heavy intensity rain
                    riscos.add("Risco: Chuva de intensidade forte detectada (" + description + ").");
                } else if (conditionId == 503) { // very heavy rain
                    riscos.add("Risco Alto: Chuva de intensidade muito forte detectada (" + description + ").");
                } else if (conditionId == 504) { // extreme rain
                    riscos.add("Risco Extremo: Chuva de intensidade extrema detectada (" + description + ").");
                } else if (conditionId == 511) { // freezing rain
                    riscos.add("Risco Alto: Chuva congelante detectada (" + description + "). Pode causar acúmulo de gelo em estruturas.");
                } else if (conditionId == 522 || conditionId == 531) { // heavy intensity shower rain / ragged shower rain
                     riscos.add("Risco Alto: Aguaceiros intensos detectados (" + description + ").");
                }
                // 6xx: Neve - Pode causar problemas em algumas regiões do Brasil
                else if (conditionId >= 600 && conditionId < 700) {
                    if (conditionId == 602 || conditionId == 622) { // Heavy snow / Heavy shower snow
                        riscos.add("Risco Alto: Neve intensa detectada (" + description + "). Pode afetar redes elétricas.");
                    }
                }
                // 7xx: Atmosfera 
                else if (conditionId == 771) { // Squalls
                    riscos.add("Risco Alto: Rajadas de vento súbitas (Squalls) detectadas (" + description + ").");
                } else if (conditionId == 781) { // Tornado
                    riscos.add("ALERTA MÁXIMO: Tornado detectado (" + description + ")! Risco iminente.");
                }
            }
        }

        // 2. Análise da Velocidade do Vento
        Wind wind = climaInfo.getWind();
        if (wind != null) {
            double velocidadeKmh = wind.getSpeedKmh();
            Double rajadaKmh = wind.getGustKmh();
            boolean ventoJaReportado = riscos.stream().anyMatch(r -> r.contains("Vento Forte") || r.contains("Rajadas") || r.contains("Squalls") || r.contains("Tempestade"));

            if (rajadaKmh != null && rajadaKmh >= LIMIAR_RAJADA_FORTE_KMH && !ventoJaReportado) {
                riscos.add(String.format("Risco Alto: Rajadas de vento muito fortes (%.1f km/h).", rajadaKmh));
            } else if (velocidadeKmh >= LIMIAR_VENTO_FORTE_KMH && !ventoJaReportado) {
                riscos.add(String.format("Risco: Vento forte (%.1f km/h).", velocidadeKmh));
            }
        }

        // 3. Análise do Volume de Chuva (se não coberto pela condição geral)
        Rain rain = climaInfo.getRain();
        if (rain != null && rain.getVolume1h() != null) {
            double chuva1h = rain.getVolume1h();
            boolean chuvaJaReportada = riscos.stream().anyMatch(r -> r.contains("Chuva") || r.contains("Aguaceiros"));

            if (chuva1h >= LIMIAR_CHUVA_MUITO_INTENSA_MMH && !chuvaJaReportada) {
                riscos.add(String.format("Risco Alto: Chuva muito intensa (%.1f mm na última hora).", chuva1h));
            } else if (chuva1h >= LIMIAR_CHUVA_INTENSA_MMH && !chuvaJaReportada) {
                 riscos.add(String.format("Risco: Chuva intensa (%.1f mm na última hora).", chuva1h));
            }
        }

        // Remove duplicatas e retorna
        return riscos.stream().distinct().collect(Collectors.toList());
    }
}

