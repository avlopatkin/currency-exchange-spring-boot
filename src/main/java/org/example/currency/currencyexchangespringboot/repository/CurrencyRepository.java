package org.example.currency.currencyexchangespringboot.repository;

import org.example.currency.currencyexchangespringboot.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {
}
