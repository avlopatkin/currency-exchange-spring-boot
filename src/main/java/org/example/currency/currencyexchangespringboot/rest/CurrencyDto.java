package org.example.currency.currencyexchangespringboot.rest;

import org.example.currency.currencyexchangespringboot.entity.Currency;

public record CurrencyDto(Long id,
                          String code,
                          String sign) {
    public CurrencyDto(Currency currency) {
        this(
                currency.getId(),
                currency.getCode(),
                currency.getSign()
        );
    }
}
