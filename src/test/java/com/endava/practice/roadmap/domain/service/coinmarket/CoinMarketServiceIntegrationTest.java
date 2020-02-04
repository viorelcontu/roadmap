package com.endava.practice.roadmap.domain.service.coinmarket;

import com.endava.practice.roadmap.config.RestTemplateConfig;
import com.endava.practice.roadmap.config.TestConfig;
import com.endava.practice.roadmap.domain.mapper.CoinMapperImpl;
import com.endava.practice.roadmap.domain.model.enums.Currency;
import com.endava.practice.roadmap.domain.model.internal.Coin;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class)
@TestPropertySource({"classpath:application.properties","classpath:api-token.properties"})
@ContextConfiguration(classes = {TestConfig.class, RestTemplateConfig.class, CoinMarketClient.class,
        CoinMapperImpl.class, CoinLookupService.class, CoinMarketService.class})
class CoinMarketServiceIntegrationTest {

    @Autowired
    private CoinMarketService coinMarketService;

    @Autowired
    private ObjectWriter objectWriter;

    @Test
    void getQuotes() {
        final Coin btc = coinMarketService.getQuotes("BTC");
        System.out.println("[QUOTE Response] " + btc);
        printSerializedObject(btc);
    }

    @Test
    void getListing() {
        final List<Coin> listing = coinMarketService.getListing();
        System.out.println("[LISTING Response] " + listing);
        printSerializedObject(listing);
    }

    @Test
    void getCurrencies() {
        final Map<Currency, String> currencies = coinMarketService.getCurrencies();
        System.out.println("[CURRENCIES Response] " + currencies);
        printSerializedObject(currencies);
    }

    @SneakyThrows
    private void printSerializedObject (Object object) {
        final String value = objectWriter.writeValueAsString(object);
        System.out.println("application/json:");
        System.out.println(value);
    }
}