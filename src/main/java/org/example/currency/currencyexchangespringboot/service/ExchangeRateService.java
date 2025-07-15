package org.example.currency.currencyexchangespringboot.service;

import lombok.RequiredArgsConstructor;
import org.example.currency.currencyexchangespringboot.entity.Currency;
import org.example.currency.currencyexchangespringboot.entity.ExchangeRate;
import org.example.currency.currencyexchangespringboot.repository.CurrencyRepository;
import org.example.currency.currencyexchangespringboot.repository.ExchangeRateRepository;
import org.example.currency.currencyexchangespringboot.rest.ConversionDto;
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
        return exchangeRateRepository.findAll().stream()
                .map(ExchangeRatesResponse::new)
                .toList();
    }

    public ExchangeRatesResponse getRate(String baseCode, String targetCode) {
        ExchangeRate rate = exchangeRateRepository.findByBaseCurrencyCodes(baseCode, targetCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exchange rate not found"));
        return new ExchangeRatesResponse(rate);
    }

    public ExchangeRatesResponse create(ExchangeRatesRequest req) {
        if (req.baseCurrencyCode() == null || req.targetCurrencyCode() == null || req.rate() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fields mustn't be null");
        }

        Currency baseCurrency = currencyRepository.findByCode(req.baseCurrencyCode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Base currency not found"));
        Currency targetCurrency = currencyRepository.findByCode(req.targetCurrencyCode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Target currency not found"));

        ExchangeRate newRate = new ExchangeRate(baseCurrency, targetCurrency, req.rate());
        ExchangeRate savedRate = exchangeRateRepository.save(newRate);
        return new ExchangeRatesResponse(savedRate);
    }

    public ExchangeRatesResponse updateRate(String baseCode, String targetCode, BigDecimal newRate) {
        ExchangeRate rate = exchangeRateRepository.findByBaseCurrencyCodes(baseCode, targetCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exchange rate not found"));

        rate.setRate(newRate);
        ExchangeRate updatedRate = exchangeRateRepository.save(rate);
        return new ExchangeRatesResponse(updatedRate);
    }

    public ConversionDto convert(String fromCurrency, String toCurrency, BigDecimal amount) {
        ExchangeRate rate = exchangeRateRepository.findByBaseCurrencyCodes(fromCurrency, toCurrency)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Exchange rate not found"));

        BigDecimal convertedAmount = amount.multiply(rate.getRate())
                .setScale(2, RoundingMode.HALF_UP);

        return new ConversionDto(rate, amount, convertedAmount);
    }
}