package com.travel.tia.service;

import com.travel.tia.domain.PlaceOverview;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class AggregatorService {

    private final GeoClient geo;
    private final WeatherClient weather;
    private final CountryClient country;
    private final FxClient fx;

    public Mono<PlaceOverview> lookup(String query) {

        log.info("Aggregation started for query={}", query);

        return geo.resolve(query)

                .doOnNext(g ->
                        log.debug("Geo result for query={}: lat={}, lon={}, name={}, countryCode={}",
                                query, g.lat(), g.lon(), g.name(), g.countryCode())
                )
                .flatMap(g -> {

                    log.debug("Calling Weather, Country and FX for query={}, code={}",
                            query, g.countryCode());

                    return Mono.zip(
                                    weather.getWeather(g.lat(), g.lon()),
                                    country.byecode(g.countryCode()),
                                    fx.latest("AZN", "USD,EUR")
                            )

                            .doOnSuccess(t ->
                                    log.debug("Weather, Country and FX responses received for query={}", query)
                            )
                            .map(t -> {
                                var overview = new PlaceOverview(
                                        query,
                                        g,
                                        t.getT1(),
                                        t.getT2(),
                                        t.getT3()
                                );

                                log.info("Aggregation completed for query={}, city={}", query, g.name());
                                return overview;



                            });
                })

                .doOnError(ex ->
                        log.error("Aggregation failed for query={}: {}", query, ex.getMessage())
                );
    }
}
