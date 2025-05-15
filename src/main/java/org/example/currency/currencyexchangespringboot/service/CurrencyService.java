package org.example.currency.currencyexchangespringboot.service;

import lombok.RequiredArgsConstructor;
import org.example.currency.currencyexchangespringboot.entity.Currency;
import org.example.currency.currencyexchangespringboot.repository.CurrencyRepository;
import org.example.currency.currencyexchangespringboot.rest.CurrencyDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public void create(Currency currency) {
        Currency entity = new Currency(currency.getCode(), currency.getSign());
        currencyRepository.save(entity);
    }

    public CurrencyDto getOne(Long id) {
        Optional<Currency> currencyOptional = currencyRepository.findById(id);
        if (currencyOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Currency not found");
        }
        Currency currency = currencyOptional.get();
        return new CurrencyDto(currency.getId(), currency.getCode(), currency.getSign());
    }
}
