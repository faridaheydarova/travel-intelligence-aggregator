package com.travel.tia.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.travel.tia.domain.WeatherNow;
import com.travel.tia.exception.WeatherServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class WeatherClient {
    private final WebClient webClient;

    public Mono<WeatherNow> getWeather(double lat, double lon) {
        return webClient.get()
                .uri("https://api.open-meteo.com/v1/forecast?latitude={lat}&longitude={lon}&current=temperature_2m,wind_speed_10m", lat, lon)
                .retrieve()
                .onStatus(status-> status.isError(),
                response-> Mono.error(new WeatherServiceException("Weather API ERROR")))
                .bodyToMono(JsonNode.class)
                .map(json -> new WeatherNow(
                        json.at("/current/temperature_2m").asDouble(),
                        json.at("/current/wind_speed_10m").asDouble(),
                        json.path("timezone").asText()
                ));
    }
}

