package org.example.currency.currencyexchangespringboot.service;

import org.example.currency.currencyexchangespringboot.rest.CurrencyDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.server.ResponseStatusException;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
public class CurrencyServiceIntegrationTest {
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("currency_exchanger_test")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private CurrencyService service;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("truncate table currencies restart identity cascade ");
    }

    @Test
    void shouldThrowNotFound_whenGetCurrencyByIdInvalid() {
        assertThrows(ResponseStatusException.class, () -> service.getCurrencyById(666L));
    }

    @Test
    void shouldThrowNotFound_whenGetCurrencyByCodeInvalid() {
        assertThrows(ResponseStatusException.class, () -> service.getCurrencyByCode("USB"));
    }

    @Test
    void shouldUpdateCurrency() {
        CurrencyDto created = service.create(new CurrencyDto(null, "USD", "$"));

        CurrencyDto updated = service.updateCurrency(created.id(), new CurrencyDto(created.id(), "EUR", "€"));

        assertEquals(created.id(), updated.id());
        assertEquals("EUR", updated.code());
        assertEquals("€", updated.sign());
    }

    @Test
    void shouldCreateCurrency() {
        CurrencyDto req = new CurrencyDto(null, "USD", "sign");

        CurrencyDto actual = service.create(req);

        assertThat(actual, allOf(
                hasProperty("id", notNullValue()),
                hasProperty("code", is("USD")),
                hasProperty("sign", is("sign"))
        ));
    }

    @Test
    void shouldSaveCurrency() {
        CurrencyDto req = new CurrencyDto(null, "USD", "sign");

        service.create(req);

        Integer count = jdbcTemplate.queryForObject("select count(*) from currencies where code = 'USD'", Integer.class);
        assertEquals(1, count);
    }

    @Test
    void shouldThrowResponseStatusException_whenCreateCurrency_ifDtoIsInvalid() {
        CurrencyDto invalid = new CurrencyDto(1L, "USD", "sign");

        var ex = assertThrows(ResponseStatusException.class,
                () -> service.create(invalid));

        assertEquals("id must be null", ex.getReason());
    }
}
