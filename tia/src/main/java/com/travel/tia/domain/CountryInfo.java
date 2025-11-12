package com.travel.tia.domain;

public record CountryInfo (
        String name, String cca2, String region,
        long population, String flagPng, String currencyCode, String currencyName, String currencySymbol
){}
