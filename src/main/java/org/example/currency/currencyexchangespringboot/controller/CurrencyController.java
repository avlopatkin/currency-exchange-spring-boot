package org.example.currency.currencyexchangespringboot.controller;

import lombok.RequiredArgsConstructor;
import org.example.currency.currencyexchangespringboot.entity.Currency;
import org.example.currency.currencyexchangespringboot.rest.CurrencyDto;
import org.example.currency.currencyexchangespringboot.service.CurrencyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/currencies")
public class CurrencyController  {

    private final CurrencyService currencyService;

    @PostMapping
    public void create (@RequestBody Currency currency) {
        currencyService.create(currency);
    }

    @GetMapping("/{id}")
    public CurrencyDto getOne(@PathVariable Long id) {
        return currencyService.getOne(id);
    }

}
