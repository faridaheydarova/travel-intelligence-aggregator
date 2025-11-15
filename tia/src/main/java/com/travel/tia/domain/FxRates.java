package com.travel.tia.domain;

import java.util.Map;

public record FxRates(
        String base, Map<String,Double> rates
) {
}
