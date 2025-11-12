package com.travel.tia.controller;

import com.travel.tia.service.GeoClient;
import com.travel.tia.service.WeatherClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/places")
public class PlaceController {
    private  final GeoClient geoClient;
    private final WeatherClient weatherClient;

    @GetMapping("/lookup")
    public Mono<Map<String, Object>> lookup(@RequestParam String query) {
        return geoClient.resolve(query)
                .flatMap(geo -> weatherClient.getWeather(geo.lat(), geo.lon())
                        .map(weather -> Map.of(
                                "query", query,
                                "location", geo,
                                "weather", weather
                        )));
    }
}
