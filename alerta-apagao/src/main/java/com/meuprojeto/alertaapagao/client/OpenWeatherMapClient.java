package com.meuprojeto.alertaapagao.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.meuprojeto.alertaapagao.model.ClimaInfo;
import com.meuprojeto.alertaapagao.util.Configuracao;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class OpenWeatherMapClient {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public OpenWeatherMapClient() {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Busca informações climáticas para uma cidade específica.
     *
     * @param cidade O nome da cidade (ex: "Sao Paulo,BR")
     * @return Um objeto ClimaInfo contendo os dados climáticos.
     * @throws IOException          Se ocorrer um erro de rede ou I/O.
     * @throws InterruptedException Se a thread for interrompida durante a requisição.
     * @throws RuntimeException     Se a API retornar um erro ou o parsing falhar.
     */
    public ClimaInfo buscarClimaPorCidade(String cidade) throws IOException, InterruptedException {
        // Codifica o nome da cidade para ser usado na URL
        String cidadeCodificada = URLEncoder.encode(cidade, StandardCharsets.UTF_8);

        // Monta a URL da API
        String url = String.format("%s?q=%s&appid=%s&lang=pt_br",
                Configuracao.OPENWEATHERMAP_API_URL,
                cidadeCodificada,
                Configuracao.OPENWEATHERMAP_API_KEY);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(10))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            try {
                // Faz o parsing do JSON para o objeto ClimaInfo
                return objectMapper.readValue(response.body(), ClimaInfo.class);
            } catch (Exception e) {
                // Lança uma exceção mais específica se o parsing falhar
                throw new RuntimeException("Falha ao fazer parsing da resposta da API: " + e.getMessage(), e);
            }
        } else {
            // Lança uma exceção se a API retornar um status diferente de 200 OK
            throw new RuntimeException("Erro ao chamar a API OpenWeatherMap. Status: " + response.statusCode() + " Corpo: " + response.body());
        }
    }
}

