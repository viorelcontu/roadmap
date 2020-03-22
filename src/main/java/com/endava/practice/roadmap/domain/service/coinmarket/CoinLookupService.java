package com.endava.practice.roadmap.domain.service.coinmarket;

import com.endava.practice.roadmap.domain.mapper.CoinMapper;
import com.endava.practice.roadmap.domain.model.exceptions.BadRequestException;
import com.endava.practice.roadmap.domain.model.external.common.ExternalCoin;
import com.endava.practice.roadmap.domain.model.internal.Coin;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toUnmodifiableList;

@Service
@RequiredArgsConstructor
public class CoinLookupService {

    private final CoinMarketClient coinMarketClient;

    private final CoinMapper coinMapper;

    @Setter(onMethod_ = {@Autowired})
    private CoinLookupService selfProxy;

    @Value("${crypto.listing.limit}")
    private int listingLimit;

    public Coin findBySymbol(String symbol) {
        return findCoin(coin -> coin.getSymbol().equalsIgnoreCase(symbol));
    }

    //TODO use this for quotes with multiple name
    public Coin findByCodeName(String codeName) {
        return findCoin(coin -> coin.getCodeName().equals(codeName));
    }

    @Cacheable("coins")
    public List<Coin> getCoinListing() {
        final List<ExternalCoin> externalCoinData = coinMarketClient.requestCoinListing(listingLimit);

        return externalCoinData.stream()
                .map(coinMapper::mapCoinListing)
                .sorted(comparing(Coin::getWorldRank))
                .collect(toUnmodifiableList());
    }

    private Coin findCoin(Predicate<Coin> predicate) {
        return selfProxy.getCoinListing()
                .stream()
                .filter(predicate)
                .findFirst()
                .orElseThrow(BadRequestException::ofWrongCrypto);
    }
}
