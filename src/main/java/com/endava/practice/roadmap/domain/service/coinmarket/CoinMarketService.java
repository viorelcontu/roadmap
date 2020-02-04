package com.endava.practice.roadmap.domain.service.coinmarket;

import com.endava.practice.roadmap.domain.mapper.CoinMapper;
import com.endava.practice.roadmap.domain.model.annotations.RequirePermission;
import com.endava.practice.roadmap.domain.model.enums.Currency;
import com.endava.practice.roadmap.domain.model.external.common.ExternalCoin;
import com.endava.practice.roadmap.domain.model.internal.Coin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.endava.practice.roadmap.domain.model.enums.Currency.currencyStringMap;
import static com.endava.practice.roadmap.domain.model.enums.Permission.SERVICES;
import static java.lang.String.valueOf;
import static java.util.function.Function.identity;

@RequiredArgsConstructor
@Service
public class CoinMarketService implements MarketService {

    private final CoinMarketClient coinMarketClient;

    private final CoinMapper coinMapper;

    private final CoinLookupService coinLookupService;

    @Override
    @RequirePermission(SERVICES)
    public Coin getQuotes(String symbol) {
        final Coin coin = coinLookupService.findBySymbol(symbol);
        final ExternalCoin externalCoin = coinMarketClient.requestMarketQuotes(coin.getExternalId());
        return coinMapper.mapCoinAndQuotes(externalCoin);
    }

    @Override
    @RequirePermission(SERVICES)
    public List<Coin> getListing() {
        return coinLookupService.getCoinListing();
    }

    @Override
    @RequirePermission(SERVICES)
    public Map<Currency, String> getCurrencies() {
        return currencyStringMap;
    }
}
