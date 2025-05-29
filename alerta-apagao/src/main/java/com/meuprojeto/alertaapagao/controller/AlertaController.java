package com.meuprojeto.alertaapagao.controller;

import com.meuprojeto.alertaapagao.model.ClimaInfo;
import com.meuprojeto.alertaapagao.service.AlertaEnvioService;
import com.meuprojeto.alertaapagao.service.ClimaConsultaService;
import com.meuprojeto.alertaapagao.service.RiscoAnaliseService;

import java.util.List;

/**
 * Controller responsável por orquestrar o fluxo de verificação de alertas.
 * Coordena a chamada aos serviços de consulta, análise e envio.
 */
public class AlertaController {

    private final ClimaConsultaService climaConsultaService;
    private final RiscoAnaliseService riscoAnaliseService;
    private final AlertaEnvioService alertaEnvioService;

    /**
     * Construtor que injeta as dependências dos serviços necessários.
     * @param climaConsultaService Serviço para consultar o clima.
     * @param riscoAnaliseService Serviço para analisar os riscos.
     * @param alertaEnvioService Serviço para enviar os alertas.
     */
    public AlertaController(ClimaConsultaService climaConsultaService,
                            RiscoAnaliseService riscoAnaliseService,
                            AlertaEnvioService alertaEnvioService) {
        this.climaConsultaService = climaConsultaService;
        this.riscoAnaliseService = riscoAnaliseService;
        this.alertaEnvioService = alertaEnvioService;
    }

    /**
     * Executa o processo de verificação de clima e envio de alertas para uma cidade.
     * @param cidade Nome da cidade a ser verificada (ex: "Sao Paulo,BR").
     */
    public void verificarEAlertarPorCidade(String cidade) {
        System.out.println("Verificando condições climáticas para: " + cidade + "...");

        // 1. Consultar Clima
        ClimaInfo clima = climaConsultaService.consultarClima(cidade);

        if (clima != null) {
            // 2. Analisar Risco
            List<String> riscos = riscoAnaliseService.analisarRisco(clima);

            // 3. Enviar Alerta (se houver risco)
            alertaEnvioService.enviarAlertaConsole(clima, riscos);
        } else {
            System.err.println("Não foi possível obter dados climáticos para " + cidade + ". Alerta não pôde ser processado.");
        }
    }
}

