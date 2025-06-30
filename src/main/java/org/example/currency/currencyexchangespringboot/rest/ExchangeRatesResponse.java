package org.example.currency.currencyexchangespringboot.rest;

import org.example.currency.currencyexchangespringboot.entity.Currency;
import org.example.currency.currencyexchangespringboot.entity.ExchangeRate;

import java.math.BigDecimal;

public record ExchangeRatesResponse(Integer id,
                                    Currency baseCurrency,
                                    Currency targetCurrency,
                                    BigDecimal rate) {
    public ExchangeRatesResponse(ExchangeRate exchangeRate) {
        this(
                exchangeRate.getId(),
                exchangeRate.getBaseCurrency(),
                exchangeRate.getTargetCurrency(),
                exchangeRate.getRate()
        );
    }
}
