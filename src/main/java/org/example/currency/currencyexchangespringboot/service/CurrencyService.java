package org.example.currency.currencyexchangespringboot.service;

import lombok.RequiredArgsConstructor;
import org.example.currency.currencyexchangespringboot.entity.Currency;
import org.example.currency.currencyexchangespringboot.repository.CurrencyRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyService {

    private final CurrencyRepository currencyRepository;

    public void create(Currency currency) {
        Currency entity = new Currency(currency.getCode(), currency.getSign());
        currencyRepository.save(entity);
    }
}
