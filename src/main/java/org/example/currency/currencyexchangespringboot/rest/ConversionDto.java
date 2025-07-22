package org.example.currency.currencyexchangespringboot.rest;

import java.math.BigDecimal;

public record ConversionDto(
        CurrencyDto baseCurrency,
        CurrencyDto targetCurrency,
        BigDecimal rate,
        BigDecimal amount,
        BigDecimal convertedAmount
) {
}
