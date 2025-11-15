package com.travel.tia.controller;

import com.travel.tia.domain.PlaceOverview;
import com.travel.tia.service.AggregatorService;
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
  private final AggregatorService agg;

    @GetMapping("/lookup")
    public Mono<PlaceOverview> lookup(@RequestParam String query) {
        return agg.lookup(query);
    }
}
