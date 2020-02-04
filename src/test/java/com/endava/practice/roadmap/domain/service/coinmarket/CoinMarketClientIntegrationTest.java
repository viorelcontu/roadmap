package com.endava.practice.roadmap.domain.service.coinmarket;

import com.endava.practice.roadmap.config.RestTemplateConfig;
import com.endava.practice.roadmap.config.TestConfig;
import com.endava.practice.roadmap.domain.model.external.common.ExternalCoin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@TestPropertySource({"classpath:application.properties","classpath:api-token.properties"})
@ContextConfiguration(classes = {TestConfig.class, RestTemplateConfig.class, CoinMarketClient.class})
class CoinMarketClientIntegrationTest {

    @Autowired
    private CoinMarketClient marketClient;

    @Autowired
    private ObjectWriter objectWriter;

    @Test
    void requestListing() throws JsonProcessingException {
        final List<ExternalCoin> externalCoinData = marketClient.requestCoinListing(20);
        final String s = objectWriter.writeValueAsString(externalCoinData);

        System.out.println(s);
    }

    @Test
    void requestQuotes() throws JsonProcessingException {
        final ExternalCoin externalCoin = marketClient.requestMarketQuotes(1);
        final String s = objectWriter.writeValueAsString(externalCoin);

        System.out.println(s);
    }
}