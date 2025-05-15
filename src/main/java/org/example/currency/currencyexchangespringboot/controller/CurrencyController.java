package org.example.currency.currencyexchangespringboot.controller;

import lombok.RequiredArgsConstructor;
import org.example.currency.currencyexchangespringboot.entity.Currency;
import org.example.currency.currencyexchangespringboot.service.CurrencyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/currencies")
public class CurrencyController  {

    private final CurrencyService currencyService;

    @PostMapping
    public void create (@RequestBody Currency currency) {
        currencyService.create(currency);
    }

}
