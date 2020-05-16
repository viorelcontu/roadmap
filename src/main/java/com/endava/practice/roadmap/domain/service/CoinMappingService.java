package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.converter.CoinConverter;
import com.endava.practice.roadmap.domain.model.enums.Currency;
import com.endava.practice.roadmap.domain.model.exceptions.BadRequestException;
import com.endava.practice.roadmap.domain.model.external.common.ExternalCoin;
import com.endava.practice.roadmap.domain.model.internal.Coin;
import com.endava.practice.roadmap.domain.service.client.CoinMarketClient;
import com.endava.practice.roadmap.web.MappingController;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static com.endava.practice.roadmap.domain.model.enums.Currency.currencyStringMap;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@RequiredArgsConstructor
public class CoinMappingService implements MappingController {

    private final CoinMarketClient coinMarketClient;

    private final CoinConverter coinConverter;

    @Setter(onMethod_ = {@Autowired})
    private CoinMappingService selfProxy;

    @Value("${crypto.listing.limit}")
    private int listingLimit;

    public Coin findBySymbol(String symbol) {
        return findCoin(coin -> coin.getSymbol().equalsIgnoreCase(symbol));
    }

    //TODO use this for quotes with multiple name
    public Coin findByCodeName(String codeName) {
        return findCoin(coin -> coin.getCodeName().equals(codeName));
    }

    @Override
    @Cacheable("coins")
    public List<Coin> getCoinListing() {
        final List<ExternalCoin> externalCoinData = coinMarketClient.requestCoinListing(listingLimit);

        return externalCoinData.stream()
                .map(coinConverter::mapCoinListing)
                .sorted(comparing(Coin::getWorldRank))
                .collect(toUnmodifiableList());
    }

    @Override
    public Map<Currency, String> getCurrencies() {
        return currencyStringMap;
    }

    private Coin findCoin(Predicate<Coin> predicate) {
        return selfProxy.getCoinListing()
                .stream()
                .filter(predicate)
                .findFirst()
                .orElseThrow(BadRequestException::ofWrongCrypto);
    }
}
