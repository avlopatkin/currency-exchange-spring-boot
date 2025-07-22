package org.example.currency.currencyexchangespringboot.rest;

import java.math.BigDecimal;

public record ExchangeRatesResponse(
        Integer id,
        CurrencyDto baseCurrency,
        CurrencyDto targetCurrency,
        BigDecimal rate
) {
}
