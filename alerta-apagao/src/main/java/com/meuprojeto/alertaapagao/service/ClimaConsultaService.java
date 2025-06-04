package com.meuprojeto.alertaapagao.service;

import com.meuprojeto.alertaapagao.client.OpenWeatherMapClient;
import com.meuprojeto.alertaapagao.model.ClimaInfo;

import java.io.IOException;

/**
 * Serviço responsável por orquestrar a consulta de dados climáticos.
 * Atua como uma camada intermediária entre o controller e o client da API.
 */
public class ClimaConsultaService {

    private final OpenWeatherMapClient apiClient;

    /**
     * Construtor que recebe uma instância do client da API.
     * @param apiClient O client para interagir com a API OpenWeatherMap.
     */
    public ClimaConsultaService(OpenWeatherMapClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Consulta os dados climáticos para uma cidade específica.
     *
     * @param cidade O nome da cidade (ex: "Sao Paulo,BR").
     * @return Um objeto ClimaInfo contendo os dados climáticos, ou null em caso de erro.
     */
    public ClimaInfo consultarClima(String cidade) {
        try {
            return apiClient.buscarClimaPorCidade(cidade);
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao consultar clima para " + cidade + ": " + e.getMessage());
            return null; // Retorna null para indicar falha na consulta
        } catch (RuntimeException e) {
            System.err.println("Erro inesperado ao consultar clima para " + cidade + ": " + e.getMessage());
            return null;
        }
    }
}

