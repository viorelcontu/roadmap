package com.endava.practice.roadmap.domain.service.coinmarket;

import com.endava.practice.roadmap.config.RestTemplateConfig;
import com.endava.practice.roadmap.config.TestConfig;
import com.endava.practice.roadmap.domain.mapper.CoinMapperImpl;
import com.endava.practice.roadmap.domain.model.enums.Currency;
import com.endava.practice.roadmap.domain.model.internal.Coin;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.Map;

@SpringJUnitConfig({TestConfig.class,
        RestTemplateConfig.class,
        CoinMarketClient.class,
        CoinMapperImpl.class,
        CoinLookupService.class,
        CoinMarketService.class})
@Slf4j
class CoinMarketServiceIntegrationTest {

    @Autowired
    private CoinMarketService coinMarketService;

    @Autowired
    private ObjectWriter objectWriter;

    @Test
    void getQuotes() {
        final Coin btc = coinMarketService.getQuotes("BTC");
        log.info("[QUOTE Response] {}", btc);
        printSerializedObject(btc);
    }

    @Test
    void getListing() {
        final List<Coin> listing = coinMarketService.getListing();
        log.info("[LISTING Response] {}", listing);
        printSerializedObject(listing);
    }

    @Test
    void getCurrencies() {
        final Map<Currency, String> currencies = coinMarketService.getCurrencies();
        log.info("[CURRENCIES Response] {}", currencies);
        printSerializedObject(currencies);
    }

    @SneakyThrows
    private void printSerializedObject (Object object) {
        final String value = objectWriter.writeValueAsString(object);
        log.info("Serialized object: \n{}", value);
    }
}