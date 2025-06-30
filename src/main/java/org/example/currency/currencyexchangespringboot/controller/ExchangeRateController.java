package org.example.currency.currencyexchangespringboot.controller;

import lombok.RequiredArgsConstructor;
import org.example.currency.currencyexchangespringboot.rest.ExchangeRatesRequest;
import org.example.currency.currencyexchangespringboot.rest.ExchangeRatesResponse;
import org.example.currency.currencyexchangespringboot.service.ExchangeRateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/exchangeRates")
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @PostMapping
    public ExchangeRatesResponse createRate(@RequestBody ExchangeRatesRequest req) {
        return exchangeRateService.create(req);
    }

    @GetMapping
    public List<ExchangeRatesResponse> getAllRates() {
        return exchangeRateService.getAllRates();
    }

    @GetMapping("{baseCode}{targetCode}")
    public ExchangeRatesResponse getExchangeRate(
            @RequestParam String baseCode,
            @RequestParam String targetCode) {
        return exchangeRateService.getRate(baseCode, targetCode);
    }
}
