package org.example.currency.currencyexchangespringboot.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.currency.currencyexchangespringboot.entity.Currency;
import org.example.currency.currencyexchangespringboot.repository.CurrencyRepository;
import org.example.currency.currencyexchangespringboot.rest.CurrencyDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyDto create(CurrencyDto req) {
        if (req.id() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "id must be null");
        }
        Currency currency = new Currency(req.code(), req.sign());
        Currency saved = currencyRepository.save(currency);
        return new CurrencyDto(saved.getId(), currency.getCode(), currency.getSign());
    }

    public CurrencyDto updateCurrency(Long id, CurrencyDto updatedCurrency) {
        Currency currency = currencyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("id not found" + id));

        currency.setCode(updatedCurrency.code());
        currency.setSign(updatedCurrency.sign());

        Currency saved = currencyRepository.save(currency);

        return new CurrencyDto(saved.getId(), saved.getCode(), saved.getSign());
    }

    public List<CurrencyDto> getCurrencies() {
        return currencyRepository.findAll().stream()
                .map(currency -> new CurrencyDto(currency.getId(), currency.getCode(), currency.getSign()))
                .toList();
    }

    public CurrencyDto getCurrencyById(Long id) {
        Currency currency = currencyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "currency not found" + id));
        return new CurrencyDto(currency.getId(), currency.getCode(), currency.getSign());
    }

    public CurrencyDto getCurrencyByCode(String code) {
        Currency currency = currencyRepository.findByCode(code)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "code not found" + code));
        return new CurrencyDto(currency.getId(), currency.getCode(), currency.getSign());
    }
}