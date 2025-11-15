package com.travel.tia.domain;

public record ErrorResponse(
        String error, String message
) {
}
