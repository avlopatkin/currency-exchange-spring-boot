package org.example.currency.currencyexchangespringboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.currency.currencyexchangespringboot.rest.CurrencyDto;
import org.example.currency.currencyexchangespringboot.service.CurrencyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CurrencyController.class)
@Import(CurrencyControllerTest.TestConfig.class)
public class CurrencyControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private ObjectMapper objectMapper;

    static class TestConfig {
        @Bean
        @Primary
        public CurrencyService currencyService() {
            return mock(CurrencyService.class);
        }
    }

    @Test
    void shouldReturnCurrencyById() throws Exception {
        when(currencyService.getCurrencyById(anyLong())).thenReturn(new CurrencyDto(12345L, "USD", "$"));
        mockMvc.perform(get("/api/currencies/12345"))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnCurrencyByCode() throws Exception {
        String code = "USD";
        when(currencyService.getCurrencyByCode(code)).thenReturn(new CurrencyDto(1L, code, "sign"));
        mockMvc.perform(get("/api/currencies/byCode/{code}", code))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreateCurrency() throws Exception {
        CurrencyDto req = new CurrencyDto(null, "USD", "sign");

        when(currencyService.create(any(CurrencyDto.class)))
                .thenReturn(new CurrencyDto(12345L, req.code(), req.sign()));

        mockMvc.perform(post("/api/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnBadRequest_whenDtoIsInvalid() throws Exception {
        CurrencyDto req = new CurrencyDto(12345L, "USD", "sign");
        when(currencyService.create(any(CurrencyDto.class))).thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid currency data"));

        mockMvc.perform(post("/api/currencies")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdateCurrency() throws Exception {
        CurrencyDto req = new CurrencyDto(null, "USD", "sign");
        CurrencyDto updated = new CurrencyDto(1L, "USD", "sign");

        when(currencyService.updateCurrency(updated.id(), req)).thenReturn(updated);

        mockMvc.perform(put("/api/currencies/{currencyId}", updated.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnAllCurrencies() throws Exception {
        List<CurrencyDto> list = List.of(
                new CurrencyDto(1L, "USD", "sign"),
                new CurrencyDto(2L, "EUR", "sign")
        );

        when(currencyService.getCurrencies()).thenReturn(list);

        mockMvc.perform(get("/api/currencies")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
