package com.travel.tia.service;

import com.fasterxml.jackson.databind.JsonNode;

import com.travel.tia.domain.GeoPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GeoClient {

    private final WebClient webClient;
    public Mono<GeoPoint> resolve(String query) {
        return webClient.get()
                .uri("https://geocoding-api.open-meteo.com/v1/search?name={name}&count=1", query)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> {
                    var r = json.path("results").get(0);
                    return new GeoPoint(
                            r.path("latitude").asDouble(),
                            r.path("longitude").asDouble(),
                            r.path("name").asText(),
                            r.path("country").asText(),
                            r.path("country_code").asText()
                    );
                });
    }

}
