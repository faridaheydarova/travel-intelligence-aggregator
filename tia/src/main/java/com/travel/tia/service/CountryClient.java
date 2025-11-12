package com.travel.tia.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.travel.tia.domain.CountryInfo;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
        @RequiredArgsConstructor
        public class CountryClient {
            public final WebClient webClient;
    private Map.Entry<String, JsonNode> l;


    public Mono<CountryInfo> byecode(String code){
                return webClient.get()
                        .uri("https://restcountries.com/v3.1/alpha/{code}",code)
                        .retrieve()
                        .bodyToMono(JsonNode.class)
                        .map(arr-> arr.get(0))
                        .map(j-> {
                           var currencies=j.path("currencies");
                           var first =currencies.fields().next();
                           var cureCode=first.getKey();
                           var cureNode=first.getValue();
                          return new CountryInfo(
                                  j.path("name").asText(),
                                  j.path("cca2").asText(),
                                  j.path("region").asText(),
                                  j.path("population").asLong(),
                                  j.at("/flags/png").asText(),
                                  cureCode,
                                  cureNode.path("name").asText(),
                                  cureNode.path("symbol").asText()
                          );

                        });


    }
}
