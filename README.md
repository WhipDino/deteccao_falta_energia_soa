# Turma 3ESR (Integrantes):

### João Victor -> RM: 550453
### Juliana Maita -> RM: 99224 
### Luana Cabezaolias -> RM: 99320 

---

# Sistema de Alerta de Risco de Apagão

## Visão Geral

Este projeto implementa um **Sistema de Alerta de Risco de Apagão** desenvolvido em Java, com arquitetura modular e separação em camadas para garantir clareza, organização e facilidade de manutenção.

O sistema simula um cenário real de monitoramento climático: consulta dados do tempo para cidades brasileiras, identifica riscos (como ventos fortes, chuvas intensas e tempestades) e gera alertas informativos no console. Assim, atende aos requisitos propostos de prevenção a possíveis faltas de energia.

---

## Estrutura do Projeto

A estrutura segue um padrão inspirado em **MVC** (Model-View-Controller), adaptado para backend. Os principais módulos estão organizados assim:

```
com.meuprojeto.alertaapagao
├── Main.java                 // Ponto de entrada da aplicação
├── controller
│   └── AlertaController.java   // Orquestra a consulta, análise e alerta
├── service
│   ├── ClimaConsultaService.java // Consulta a API OpenWeatherMap
│   ├── RiscoAnaliseService.java  // Analisa os dados climáticos
│   └── AlertaEnvioService.java   // Envia alertas para o console
├── client                   // Ou 'repository' ou 'gateway'
│   └── OpenWeatherMapClient.java // Lógica de chamada HTTP para a API
├── model                    // Ou 'dto' ou 'domain'
│   ├── ClimaInfo.java          // Representa os dados climáticos da API
│   └── Alerta.java             // Representa a informação do alerta
└── util
    └── Configuracao.java       // Gerencia configurações (ex: API Key)
```

## Componentes e Responsabilidades

- **Main / AlertaController:**  
  Inicia o sistema, define a lista de cidades monitoradas e coordena o fluxo de execução (consulta, análise, alerta).

- **ClimaConsultaService:**  
  Usa o `OpenWeatherMapClient` para buscar os dados de clima em tempo real e tratar possíveis erros de comunicação.

- **OpenWeatherMapClient:**  
  Implementa a lógica de requisições HTTP para a API do OpenWeatherMap, incluindo uso da chave de API e tratamento do JSON de resposta.

- **RiscoAnaliseService:**  
  Recebe os dados meteorológicos e identifica situações de risco relevantes (chuva forte, ventos extremos, tempestades, etc.), baseando-se em limiares técnicos e códigos da própria API.

- **AlertaEnvioService:**  
  Exibe alertas formatados no console, incluindo recomendações para os moradores se prepararem/prevenirem.

- **model:**  
  Classes POJO que representam os dados vindos da API e manipulados internamente (`ClimaInfo`, `Weather`, `Wind`, `Rain`, etc.).

- **util:**  
  Classes utilitárias, como configuração da chave da API.

---

## Justificativa das Escolhas

- **Modularidade:**  
  Cada serviço foi separado em sua responsabilidade, facilitando testes, manutenção e futuras expansões (exemplo: envio de alertas por e-mail).

- **Separação em camadas:**  
  Controller, Service e Client desacoplam lógica de orquestração, de negócio e de integração externa, tornando o código limpo e escalável.

- **Java:**  
  Linguagem robusta, solicitada explicitamente no desafio, e padrão de mercado para aplicações backend.

- **Alertas no console:**  
  Mantém o protótipo simples, focando na lógica central do sistema e facilitando validação dos resultados.

- **Monitoramento nacional:**  
  Para este protótipo, monitora cidades representativas do Brasil, demonstrando a viabilidade do sistema sem sobrecarregar limites da API.

---

## Lógica de Análise de Risco (`RiscoAnaliseService`)

*   **Escolha:** Basear a análise em:
    *   Códigos de condição da OpenWeatherMap (ex: códigos para tempestade, chuva extrema).
    *   Limiares (valores limite) para velocidade do vento, rajadas e volume de chuva (mm/h).
*   **Por quê?** Tentamos cobrir os cenários mais óbvios que podem levar à falta de energia, conforme a documentação da própria API e o bom senso:
    *   **Tempestades elétricas (raios):** Risco claro.
    *   **Ventos muito fortes ou rajadas:** Podem derrubar postes e árvores.
    *   **Chuvas torrenciais:** Podem causar inundações e danos.
    *   **Outros eventos extremos:** Como tornados (raros, mas críticos) ou chuva congelante.
*   **Flexibilidade:** Os valores limite (limiares) estão definidos no código (`RiscoAnaliseService`) e podem ser ajustados se a experiência mostrar que precisam ser mais ou menos sensíveis.

---
## Fluxo de Funcionamento

1. **O programa inicia** e define uma lista de cidades para monitorar.
2. **Consulta a API OpenWeatherMap** para obter as condições atuais de cada cidade.
3. **Analisa os dados recebidos** para identificar riscos (chuva forte, ventos, tempestades, etc).
4. **Se detectar risco, gera e exibe um alerta** informativo e preventivo no console, com orientações para a população.

---

## Tecnologias Utilizadas

- **Java 17**
- **Maven** (gerenciamento de dependências e build)
- **Jackson** (conversão JSON <-> Java)
- **HttpClient** (requisições HTTP nativas Java)
- **JUnit** (testes unitários)

---

## Como Rodar o Projeto

Siga as etapas abaixo para executar o sistema localmente:

1. **Pré-requisitos**
   
   - Certifique-se de ter o **Java 17** (ou superior) e o **Maven** instalados na sua máquina.

2. **Download e Extração**
   
   - Baixe o projeto (arquivo `.zip`) e extraia o conteúdo para uma pasta de sua preferência.

3. **Acesse o Diretório do Projeto**
   
   - Abra o terminal e navegue até a pasta onde você extraiu o projeto (a pasta que contém o arquivo `pom.xml`).

4. **Compilação**
   
   - Execute o comando abaixo para baixar as dependências e compilar o projeto:
     ```bash
     mvn clean package
     ```
   - Esse comando irá criar um arquivo `.jar` executável dentro da pasta `target`.

5. **Execução**
   
   - Ainda no terminal, navegue até a pasta `target` e execute o comando:
     ```bash
     java -jar alerta-apagao-1.0-SNAPSHOT-jar-with-dependencies.jar
     ```

Pronto! O sistema irá iniciar e os alertas serão exibidos diretamente no console.


---
## Exemplos de Alertas Gerados

### Nenhum risco foi detectado:
![Imagem do WhatsApp de 2025-05-29 à(s) 16 22 50_58300b01](https://github.com/user-attachments/assets/cec255f5-8b41-481b-9fe7-3dfd19448b07)

### Alteramos a sensibilidade dos sensores para detectar risco em Recife:
![Imagem do WhatsApp de 2025-05-29 à(s) 16 25 14_a1c8bd00](https://github.com/user-attachments/assets/bb9a3a91-ac1c-4992-b30a-31911de769fb)


