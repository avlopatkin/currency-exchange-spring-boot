package org.example.currency.currencyexchangespringboot.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "exchange_rates")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExchangeRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "base_currency_id")
    private Currency baseCurrency;

    @ManyToOne
    @JoinColumn(name = "target_currency_id")
    private Currency targetCurrency;

    @Column(name = "rate")
    private BigDecimal rate;

    public ExchangeRate(Currency baseCurrency, Currency targetCurrency, BigDecimal rate) {
        this.baseCurrency = baseCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = rate;
    }
}
