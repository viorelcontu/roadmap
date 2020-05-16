package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.converter.CoinConverter;
import com.endava.practice.roadmap.domain.model.annotations.RequirePermission;
import com.endava.practice.roadmap.domain.model.external.common.ExternalCoin;
import com.endava.practice.roadmap.domain.model.internal.Coin;
import com.endava.practice.roadmap.domain.service.client.CoinMarketClient;
import com.endava.practice.roadmap.web.LookupController;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import static com.endava.practice.roadmap.domain.model.enums.Permission.SERVICES;

@RequiredArgsConstructor
@Service
@RequirePermission(SERVICES)
public class LookupService implements LookupController {

    private final CoinMarketClient coinMarketClient;

    private final CoinConverter coinConverter;

    private final CoinMappingService coinMappingService;

    @Override
    @Cacheable(value = "quotes", key = "#symbol")
    public Coin getQuote(String symbol) {
        final Coin coin = coinMappingService.findBySymbol(symbol);
        final ExternalCoin externalCoin = coinMarketClient.requestMarketQuotes(coin.getExternalId());
        return coinConverter.mapCoinAndQuotes(externalCoin);
    }
}
