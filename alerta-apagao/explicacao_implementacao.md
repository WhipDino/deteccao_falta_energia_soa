# Explicação das Escolhas de Implementação e Arquitetura

Este documento explica as principais decisões tomadas durante o desenvolvimento do Sistema de Alerta de Risco de Apagão, focando no "porquê" por trás de cada escolha, de forma acessível a quem não é da área técnica.

## 1. Linguagem de Programação: Java

*   **Escolha:** Java.
*   **Por quê?** Esta foi uma solicitação direta nos requisitos do projeto. Java é uma linguagem robusta, amplamente utilizada no mercado, especialmente para aplicações de backend (que rodam em servidores) como esta.

## 2. Arquitetura: Modular e em Camadas

*   **Escolha:** Dividir o sistema em módulos (pequenos blocos com funções específicas) e camadas (Controle, Serviço, Cliente/Dados).
*   **Por quê?** Imagine construir com peças de LEGO. Cada peça (módulo/camada) tem uma função clara:
    *   **Cliente (`OpenWeatherMapClient`):** É o "mensageiro" que sabe como conversar com a API do OpenWeatherMap para buscar os dados do tempo.
    *   **Serviços (`ClimaConsultaService`, `RiscoAnaliseService`, `AlertaEnvioService`):** São os "cérebros" que realizam as tarefas principais:
        *   `ClimaConsultaService`: Pede ao "mensageiro" para buscar os dados.
        *   `RiscoAnaliseService`: Analisa os dados recebidos para ver se há perigo (chuva forte, vento, etc.).
        *   `AlertaEnvioService`: Formata e mostra o alerta no console se houver perigo.
    *   **Controle (`AlertaController`):** É o "maestro" que organiza o fluxo, dizendo qual serviço chamar e em que ordem.
    *   **Modelos (`ClimaInfo`, `Weather`, etc.):** São como "fichas" que guardam as informações de forma organizada (temperatura, vento, chuva).
*   **Benefícios:** Essa organização torna o sistema:
    *   **Mais fácil de entender:** Cada parte tem sua responsabilidade bem definida.
    *   **Mais fácil de testar:** Podemos testar cada "peça" separadamente.
    *   **Mais fácil de modificar:** Se quisermos mudar como os alertas são enviados (ex: adicionar e-mail), mexemos apenas no `AlertaEnvioService`, sem afetar o resto. Se a API do OpenWeatherMap mudar, ajustamos principalmente o `OpenWeatherMapClient` e os `Modelos`.

## 3. Acesso à API OpenWeatherMap

*   **Escolha:** Usar a biblioteca `HttpClient` nativa do Java (a partir do Java 11).
*   **Por quê?** É a forma moderna e padrão do Java para fazer requisições web (como buscar dados na API). Não precisamos adicionar muitas bibliotecas externas complexas para esta tarefa básica.
*   **JSON e Jackson:** A API devolve dados em formato JSON (um texto estruturado). Usamos a biblioteca `Jackson`, que é muito popular em Java, para converter esse texto automaticamente para nossas "fichas" (`Modelos`), tornando o uso dos dados muito mais fácil no código.

## 4. Lógica de Análise de Risco (`RiscoAnaliseService`)

*   **Escolha:** Basear a análise em:
    *   Códigos de condição da OpenWeatherMap (ex: códigos para tempestade, chuva extrema).
    *   Limiares (valores limite) para velocidade do vento, rajadas e volume de chuva (mm/h).
*   **Por quê?** Tentamos cobrir os cenários mais óbvios que podem levar à falta de energia, conforme a documentação da própria API e o bom senso:
    *   **Tempestades elétricas (raios):** Risco claro.
    *   **Ventos muito fortes ou rajadas:** Podem derrubar postes e árvores.
    *   **Chuvas torrenciais:** Podem causar inundações e danos.
    *   **Outros eventos extremos:** Como tornados (raros, mas críticos) ou chuva congelante.
*   **Flexibilidade:** Os valores limite (limiares) estão definidos no código (`RiscoAnaliseService`) e podem ser ajustados se a experiência mostrar que precisam ser mais ou menos sensíveis.

## 5. Envio de Alertas (`AlertaEnvioService`)

*   **Escolha:** Exibir os alertas diretamente no console (a tela onde o programa é executado).
*   **Por quê?** Foi o solicitado para este protótipo, mantendo a simplicidade. A estrutura modular permite que, no futuro, este serviço seja modificado para enviar e-mails, SMS, etc., sem impactar a lógica de consulta ou análise.

## 6. Gerenciamento do Projeto: Maven

*   **Escolha:** Usar o Maven.
*   **Por quê?** É uma ferramenta padrão no mundo Java para gerenciar as "dependências" (bibliotecas que o projeto usa, como a Jackson) e para "construir" o projeto (compilar o código e empacotar tudo para execução).

## 7. Tratamento de Erros

*   **Escolha:** Usar blocos `try-catch` para capturar erros esperados (ex: falha na conexão com a API) e exibir mensagens no console.
*   **Por quê?** Para evitar que o programa simplesmente "quebre" ou feche inesperadamente se algo der errado (como a internet cair ou a API ficar indisponível). Ele tenta lidar com o erro e continuar (ou pelo menos informar o problema).

## 8. Configuração (`Configuracao.java`)

*   **Escolha:** Uma classe simples para guardar a chave da API e a URL base.
*   **Por quê?** Centraliza informações que podem precisar ser alteradas, facilitando a manutenção.

## 9. Monitoramento de Cidades (`Main.java`)

*   **Escolha:** Uma lista fixa de cidades brasileiras representativas.
*   **Por quê?** Consultar *todas* as cidades do Brasil continuamente seria inviável (limites da API, custo, complexidade). Para um protótipo, uma lista de grandes centros urbanos de diferentes regiões é suficiente para demonstrar o funcionamento.

Esperamos que esta explicação ajude a entender as decisões por trás da construção do sistema!
