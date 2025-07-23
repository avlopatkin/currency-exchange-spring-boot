package org.example.currency.currencyexchangespringboot.service;

import lombok.RequiredArgsConstructor;
import org.example.currency.currencyexchangespringboot.entity.Currency;
import org.example.currency.currencyexchangespringboot.entity.ExchangeRate;
import org.example.currency.currencyexchangespringboot.repository.CurrencyRepository;
import org.example.currency.currencyexchangespringboot.repository.ExchangeRateRepository;
import org.example.currency.currencyexchangespringboot.rest.ConversionDto;
import org.example.currency.currencyexchangespringboot.rest.CurrencyDto;
import org.example.currency.currencyexchangespringboot.rest.ExchangeRatesRequest;
import org.example.currency.currencyexchangespringboot.rest.ExchangeRatesResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExchangeRateService {
    private final ExchangeRateRepository exchangeRateRepository;
    private final CurrencyRepository currencyRepository;

    public List<ExchangeRatesResponse> getAllRates() {
        return exchangeRateRepository.findRateWithCurrencies().stream()
                .map(exchangeRate -> new ExchangeRatesResponse(
                        exchangeRate.getId(),
                        new CurrencyDto(
                                exchangeRate.getBaseCurrency().getId(),
                                exchangeRate.getBaseCurrency().getCode(),
                                exchangeRate.getBaseCurrency().getSign()),
                        new CurrencyDto(
                                exchangeRate.getTargetCurrency().getId(),
                                exchangeRate.getTargetCurrency().getCode(),
                                exchangeRate.getTargetCurrency().getSign()),
                        exchangeRate.getRate()
                ))
                .toList();
    }

    public ExchangeRatesResponse getRate(String baseCode, String targetCode) {
        ExchangeRate rate = exchangeRateRepository.findByBaseCurrencyCodesWithCurrencies(baseCode, targetCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exchange rate not found"));
        return new ExchangeRatesResponse(
                rate.getId(),
                new CurrencyDto(
                        rate.getBaseCurrency().getId(),
                        rate.getBaseCurrency().getCode(),
                        rate.getBaseCurrency().getSign()),
                new CurrencyDto(
                        rate.getTargetCurrency().getId(),
                        rate.getTargetCurrency().getCode(),
                        rate.getTargetCurrency().getSign()),
                rate.getRate());
    }

    public ExchangeRatesResponse create(ExchangeRatesRequest req) {
        if (req.baseCurrencyCode() == null || req.targetCurrencyCode() == null || req.rate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fields mustn't be null");
        }

        Currency baseCurrency = currencyRepository.findByCode(req.baseCurrencyCode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Base currency not found"));
        Currency targetCurrency = currencyRepository.findByCode(req.targetCurrencyCode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Target currency not found"));

        ExchangeRate savedRate = exchangeRateRepository.save(new ExchangeRate(baseCurrency, targetCurrency, req.rate()));
        return new ExchangeRatesResponse(savedRate.getId(),
                new CurrencyDto(
                        savedRate.getBaseCurrency().getId(),
                        savedRate.getBaseCurrency().getCode(),
                        savedRate.getBaseCurrency().getSign()),
                new CurrencyDto(
                        savedRate.getTargetCurrency().getId(),
                        savedRate.getTargetCurrency().getCode(),
                        savedRate.getTargetCurrency().getSign()),
                savedRate.getRate());
    }

    public ExchangeRatesResponse updateRate(String baseCode, String targetCode, BigDecimal newRate) {
        ExchangeRate rate = exchangeRateRepository.findByBaseCurrencyCodesWithCurrencies(baseCode, targetCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exchange rate not found"));

        rate.setRate(newRate);
        ExchangeRate updatedRate = exchangeRateRepository.save(rate);
        return new ExchangeRatesResponse(updatedRate.getId(),
                new CurrencyDto(
                        updatedRate.getBaseCurrency().getId(),
                        updatedRate.getBaseCurrency().getCode(),
                        updatedRate.getBaseCurrency().getSign()),
                new CurrencyDto(
                        updatedRate.getTargetCurrency().getId(),
                        updatedRate.getTargetCurrency().getCode(),
                        updatedRate.getTargetCurrency().getSign()),
                updatedRate.getRate());
    }

    public ConversionDto convert(String fromCurrency, String toCurrency, BigDecimal amount) {
        ExchangeRate rate = exchangeRateRepository.findByBaseCurrencyCodesWithCurrencies(fromCurrency, toCurrency)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exchange rate not found"));

        BigDecimal convertedAmount = amount.multiply(rate.getRate())
                .setScale(2, RoundingMode.HALF_UP);

        return new ConversionDto(new CurrencyDto(
                rate.getBaseCurrency().getId(),
                rate.getBaseCurrency().getCode(),
                rate.getBaseCurrency().getSign()),
                new CurrencyDto(
                        rate.getTargetCurrency().getId(),
                        rate.getTargetCurrency().getCode(),
                        rate.getTargetCurrency().getSign()),
                rate.getRate(),
                amount,
                convertedAmount);
    }
}