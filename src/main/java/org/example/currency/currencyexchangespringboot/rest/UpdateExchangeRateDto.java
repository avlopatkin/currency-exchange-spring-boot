package org.example.currency.currencyexchangespringboot.rest;

import java.math.BigDecimal;

public record UpdateExchangeRateDto(BigDecimal rate) {}