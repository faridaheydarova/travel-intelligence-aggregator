package com.travel.tia.service;

import com.fasterxml.jackson.databind.JsonNode;

import com.travel.tia.domain.GeoPoint;
import com.travel.tia.exception.GeoNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Slf4j
@Service
@RequiredArgsConstructor
public class GeoClient {

    private final WebClient webClient;
    public Mono<GeoPoint> resolve(String query) {
        log.info("Geo lookup started. query={}", query);
        return webClient.get()
                .uri("https://geocoding-api.open-meteo.com/v1/search?name={name}&count=1", query)
                .retrieve()

                .bodyToMono(JsonNode.class)

                .map(json -> {
                    JsonNode results=json.path("results");
                    if(!results.isArray() || results.isEmpty()){
                        log.warn("Geo API returned empty result for query={}", query);
                        throw new GeoNotFoundException("Location not found for query: " + query);
                    }

                    JsonNode r = results.get(0);
                    double lat = r.path("latitude").asDouble();
                    double lon = r.path("longitude").asDouble();
                    log.debug("Geo API response parsed: lat={}, lon={}", lat, lon);
                    return new GeoPoint(
                           lat,lon,
                            r.path("name").asText(),
                            r.path("country").asText(),
                            r.path("country_code").asText()
                    );

                });
    }

}
