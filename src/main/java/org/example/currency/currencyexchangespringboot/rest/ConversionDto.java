package org.example.currency.currencyexchangespringboot.rest;

import org.example.currency.currencyexchangespringboot.entity.ExchangeRate;

import java.math.BigDecimal;

public record ConversionDto(
        CurrencyDto baseCurrency,
        CurrencyDto targetCurrency,
        BigDecimal rate,
        BigDecimal amount,
        BigDecimal convertedAmount
) {
    public ConversionDto(ExchangeRate rate, BigDecimal amount, BigDecimal convertedAmount) {
        this(
                new CurrencyDto(rate.getBaseCurrency()),
                new CurrencyDto(rate.getTargetCurrency()),
                rate.getRate(),
                amount,
                convertedAmount
        );
    }
}
