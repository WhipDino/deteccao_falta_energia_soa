package com.meuprojeto.alertaapagao;

import com.meuprojeto.alertaapagao.client.OpenWeatherMapClient;
import com.meuprojeto.alertaapagao.controller.AlertaController;
import com.meuprojeto.alertaapagao.service.AlertaEnvioService;
import com.meuprojeto.alertaapagao.service.ClimaConsultaService;
import com.meuprojeto.alertaapagao.service.RiscoAnaliseService;

import java.util.Arrays;
import java.util.List;

/**
 * Ponto de entrada principal da aplicação de Alerta de Apagão.
 * Configura e inicia o processo de verificação climática.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Alerta de Risco de Apagão...");

        // --- Injeção de Dependências (Simples) ---
        OpenWeatherMapClient apiClient = new OpenWeatherMapClient();
        ClimaConsultaService climaConsultaService = new ClimaConsultaService(apiClient);
        RiscoAnaliseService riscoAnaliseService = new RiscoAnaliseService();
        AlertaEnvioService alertaEnvioService = new AlertaEnvioService();
        AlertaController alertaController = new AlertaController(climaConsultaService, riscoAnaliseService, alertaEnvioService);

        // --- Lista de Cidades para Monitorar (Representativas do Brasil) ---
        List<String> cidadesParaMonitorar = Arrays.asList(
                "Sao Paulo,BR",
                "Rio de Janeiro,BR",
                "Belo Horizonte,BR",
                "Brasilia,BR",
                "Salvador,BR",
                "Fortaleza,BR",
                "Curitiba,BR",
                "Manaus,BR",
                "Recife,BR",
                "Porto Alegre,BR"
        );

        // --- Executar Verificação para Cada Cidade ---
        for (String cidade : cidadesParaMonitorar) {
            alertaController.verificarEAlertarPorCidade(cidade);
            // Pausa entre requisições para não sobrecarregar a API
            try {
                Thread.sleep(1000); // Pausa de 1 segundo
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Thread interrompida durante a pausa.");
            }
        }

        System.out.println("\nVerificação concluída para todas as cidades.");
    }
}

