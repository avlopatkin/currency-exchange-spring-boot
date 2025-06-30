package org.example.currency.currencyexchangespringboot.controller;

import lombok.RequiredArgsConstructor;
import org.example.currency.currencyexchangespringboot.entity.Currency;
import org.example.currency.currencyexchangespringboot.rest.CurrencyDto;
import org.example.currency.currencyexchangespringboot.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/currencies")
public class CurrencyController  {
    private final CurrencyService currencyService;

    @PostMapping
    public CurrencyDto createCurrency(@RequestBody CurrencyDto req) {
        return currencyService.create(req);
    }

    @PutMapping("{currencyId}")
    public CurrencyDto updateCurrency(@PathVariable Long currencyId, @RequestBody CurrencyDto updatedCurrency) {
        return currencyService.updateCurrency(currencyId, updatedCurrency);
    }

    @GetMapping
    public List<CurrencyDto> getCurrencies() {
        return currencyService.getCurrencies();
    }

    @GetMapping("{currencyId}")
    public CurrencyDto getCurrencyById(@PathVariable Long currencyId) {
        return currencyService.getCurrencyById(currencyId);
    }

    @GetMapping("/byCode/{code}")
    public CurrencyDto getCurrencyByCode(@PathVariable String code) {
        return currencyService.getCurrencyByCode(code);
    }
}
