package com.meuprojeto.alertaapagao.service;

import com.meuprojeto.alertaapagao.model.ClimaInfo;
import java.util.List;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Serviço responsável por formatar e enviar alertas para o console.
 */
public class AlertaEnvioService {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss z")
                                                                    .withZone(ZoneId.systemDefault()); // Usa o fuso horário do sistema

    /**
     * Envia um alerta formatado para o console se houver riscos identificados.
     *
     * @param climaInfo Dados climáticos (usado para obter nome da cidade e timestamp).
     * @param riscos    Lista de strings descrevendo os riscos identificados pelo RiscoAnaliseService.
     */
    public void enviarAlertaConsole(ClimaInfo climaInfo, List<String> riscos) {
        if (riscos == null || riscos.isEmpty()) {
            // Nenhum risco detectado, não envia alerta
            return;
        }

        String nomeCidade = climaInfo != null ? climaInfo.getName() : "Local Desconhecido";
        String timestamp = climaInfo != null ? formatter.format(Instant.ofEpochSecond(climaInfo.getDt())) : "Horário Desconhecido";

        System.out.println("\n============================================================");
        System.out.println("          *** ALERTA DE RISCO DE APAGÃO ***");
        System.out.println("============================================================");
        System.out.println("Local: " + nomeCidade);
        System.out.println("Horário da Verificação: " + timestamp);
        System.out.println("------------------------------------------------------------");
        System.out.println("Condições de Risco Detectadas:");
        for (String risco : riscos) {
            System.out.println("- " + risco);
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("Recomendações Preventivas:");
        System.out.println("* Carregue completamente celulares, power banks e outros dispositivos essenciais.");
        System.out.println("* Tenha lanternas e pilhas extras à mão.");
        System.out.println("* Desconecte aparelhos eletrônicos sensíveis da tomada para evitar danos por surtos de tensão.");
        System.out.println("* Se possível, tenha um estoque de água potável e alimentos não perecíveis.");
        System.out.println("* Evite sair de casa durante tempestades severas.");
        System.out.println("* Mantenha-se informado através de fontes oficiais sobre a situação.");
        System.out.println("============================================================\n");
    }
}

