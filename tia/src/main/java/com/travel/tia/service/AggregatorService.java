package com.travel.tia.service;

import com.travel.tia.domain.PlaceOverview;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AggregatorService {
    public final GeoClient geo;
    public final WeatherClient weather;
    public final CountryClient country;
    public final FxClient fx;

    public Mono<PlaceOverview> lookup(String query){
    return geo.resolve(query)
            .flatMap(g-> Mono.zip(
weather.getWeather(g.lat(),g.lon()),
                    country.byecode(g.countryCode()),
                    fx.latest("AZN","EU,USD"))
                    .map(t -> new PlaceOverview(query, g, t.getT1(), t.getT2(), t.getT3()))
            );

    }
}
