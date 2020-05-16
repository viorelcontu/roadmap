package com.endava.practice.roadmap.domain.service.client;

import com.endava.practice.roadmap.config.RestTemplateConfig;
import com.endava.practice.roadmap.config.TestConfig;
import com.endava.practice.roadmap.domain.converter.CoinConverterImpl;
import com.endava.practice.roadmap.domain.model.internal.Coin;
import com.endava.practice.roadmap.domain.service.CoinMappingService;
import com.endava.practice.roadmap.domain.service.LookupService;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig({TestConfig.class,
        RestTemplateConfig.class,
        CoinMarketClient.class,
        CoinConverterImpl.class,
        CoinMappingService.class,
        LookupService.class})
@Slf4j
class LookupServiceIntegrationTest {

    @Autowired
    private LookupService coinLookupService;

    @Autowired
    private ObjectWriter objectWriter;

    @Test
    void getQuotes() {
        final Coin btc = coinLookupService.getQuote("BTC");
        log.info("[QUOTE Response] {}", btc);
        printSerializedObject(btc);
    }

    @SneakyThrows
    private void printSerializedObject (Object object) {
        final String value = objectWriter.writeValueAsString(object);
        log.info("Serialized object: \n{}", value);
    }
}