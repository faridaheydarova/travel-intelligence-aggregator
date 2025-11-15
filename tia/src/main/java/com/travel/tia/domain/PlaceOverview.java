package com.travel.tia.domain;

public record PlaceOverview(
        String query, GeoPoint location, WeatherNow weather,
        CountryInfo country, FxRates fx
) {
}
