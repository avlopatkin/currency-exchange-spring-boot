package org.example.currency.currencyexchangespringboot.rest;

import java.math.BigDecimal;

public record ExchangeRatesRequest(
        String baseCurrencyCode,
        String targetCurrencyCode,
        BigDecimal rate
) {}
