package com.travel.tia.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.tia.domain.FxRates;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
<<<<<<< Updated upstream
public class FxClient {
=======

        /*public class FxClient {  //api doesnt work because is not free
>>>>>>> Stashed changes
    public final WebClient webClient;

    public Mono<FxRates> latest(String base, String symbolsCsv){
        return webClient.get()
                .uri("https://api.exchangerate.host/latest?base={b}&symbols={s}",base,symbolsCsv)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(jsonNode ->
                new FxRates(jsonNode.path("base").asText(),
                new com.fasterxml.jackson.databind.ObjectMapper()
                .convertValue(jsonNode.path("rates"), new com.fasterxml.jackson.core.type.TypeReference<Map<String,Double>>(){})));
    }

    }


<<<<<<< Updated upstream
=======
*/




public class FxClient {

    public Mono<FxRates> latest(String base, String symbolsCsv) {
        Map<String, Double> rates = Map.of(
                "USD", 0.59,
                "EUR", 0.54
        );
        return Mono.just(new FxRates(base, rates));
    }
}
>>>>>>> Stashed changes
