package org.example.currency.currencyexchangespringboot.controller;

import lombok.RequiredArgsConstructor;
import org.example.currency.currencyexchangespringboot.rest.ConversionDto;
import org.example.currency.currencyexchangespringboot.rest.ExchangeRateUpdateDto;
import org.example.currency.currencyexchangespringboot.rest.ExchangeRatesRequest;
import org.example.currency.currencyexchangespringboot.rest.ExchangeRatesResponse;
import org.example.currency.currencyexchangespringboot.service.ExchangeRateService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @GetMapping("/exchangeRate")
    public ExchangeRatesResponse getExchangeRate(
            @RequestParam String baseCode,
            @RequestParam String targetCode) {
        return exchangeRateService.getRate(baseCode, targetCode);
    }

    @GetMapping("/exchange")
    public ConversionDto convert(
            @RequestParam("from") String fromCurrency,
            @RequestParam("to") String toCurrency,
            @RequestParam("amount") BigDecimal amount
            ) {
        return exchangeRateService.convert(fromCurrency, toCurrency, amount);
    }

    @PatchMapping
    public ExchangeRatesResponse updateExchangeRate(
            @RequestParam("baseCode") String baseCode,
            @RequestParam("targetCode") String targetCode,
            @RequestBody ExchangeRateUpdateDto request
            ) {
        return exchangeRateService.updateRate(baseCode, targetCode, request.rate());
    }
}
