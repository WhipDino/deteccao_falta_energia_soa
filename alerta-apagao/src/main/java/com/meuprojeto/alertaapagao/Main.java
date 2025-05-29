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
        // Em uma aplicação maior, usaríamos um framework de injeção (Spring, Guice)
        OpenWeatherMapClient apiClient = new OpenWeatherMapClient();
        ClimaConsultaService climaConsultaService = new ClimaConsultaService(apiClient);
        RiscoAnaliseService riscoAnaliseService = new RiscoAnaliseService();
        AlertaEnvioService alertaEnvioService = new AlertaEnvioService();
        AlertaController alertaController = new AlertaController(climaConsultaService, riscoAnaliseService, alertaEnvioService);

        // --- Lista de Cidades para Monitorar (Representativas do Brasil) ---
        // A API pode ter limites, então usamos uma lista gerenciável.
        // Adicionar ",BR" para especificar o país e evitar ambiguidades.
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
                // Adicione mais cidades se necessário e permitido pela API
        );

        // --- Executar Verificação para Cada Cidade ---
        for (String cidade : cidadesParaMonitorar) {
            alertaController.verificarEAlertarPorCidade(cidade);
            // Pausa opcional entre requisições para não sobrecarregar a API
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

