package org.example.currency.currencyexchangespringboot.repository;

import org.example.currency.currencyexchangespringboot.entity.ExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {
    @Query(value = """
        WITH currency_ids AS (
            SELECT
                (SELECT id FROM currencies WHERE code = :baseCode) AS base_id,
                (SELECT id FROM currencies WHERE code = :targetCode) AS target_id
        )
        SELECT er.*
        FROM exchange_rates er
        JOIN currency_ids ON true
        WHERE
            er.base_currency_id = currency_ids.base_id
            AND er.target_currency_id = currency_ids.target_id
        """,
            nativeQuery = true)

    Optional<ExchangeRate> findByBaseCurrencyCodes(
            @Param("baseCode") String baseCode,
            @Param("targetCode") String targetCode
    );
}
