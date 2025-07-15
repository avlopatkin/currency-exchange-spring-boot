package org.example.currency.currencyexchangespringboot.rest;

import org.example.currency.currencyexchangespringboot.entity.ExchangeRate;

import java.math.BigDecimal;

public record ExchangeRatesResponse(
        Integer id,
        CurrencyDto baseCurrency,
        CurrencyDto targetCurrency,
        BigDecimal rate
) {
    public ExchangeRatesResponse(ExchangeRate exchangeRate) {
        this(
                exchangeRate.getId(),
                new CurrencyDto(exchangeRate.getBaseCurrency()),
                new CurrencyDto(exchangeRate.getTargetCurrency()),
                exchangeRate.getRate()
        );
    }
}
