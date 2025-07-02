package org.example.currency.currencyexchangespringboot.rest;

import java.math.BigDecimal;

public record ConversionDto(
        String fromCurrency,
        String toCurrency,
        BigDecimal amount,
        BigDecimal convertedAmount,
        BigDecimal rate
) {}
