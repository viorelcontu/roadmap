package com.endava.practice.roadmap.domain.service.coinmarket;

import com.endava.practice.roadmap.domain.mapper.CoinMapper;
import com.endava.practice.roadmap.domain.model.exceptions.ResourceNotFoundException;
import com.endava.practice.roadmap.domain.model.external.common.ExternalCoin;
import com.endava.practice.roadmap.domain.model.internal.Coin;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class CoinLookupService {

    private final CoinMarketClient coinMarketClient;

    private final CoinMapper coinMapper;

    @Value("${crypto.listing.limit}")
    private int listingLimit;

    public Coin findBySymbol(String symbol) {
        return findCoin(coin -> coin.getSymbol().toLowerCase().equals(symbol.toLowerCase()));
    }

    //TODO use this for quotes with multiple name
    public Coin findByCodeName(String codeName) {
        return findCoin(coin -> coin.getCodeName().equals(codeName));
    }

    //TODO this should be cacheable
    public List<Coin> getCoinListing() {
        final List<ExternalCoin> externalCoinData = coinMarketClient.requestCoinListing(listingLimit);

        return externalCoinData.stream()
                .map(coinMapper::mapCoinListing)
                .sorted(comparing(Coin::getWorldRank))
                .collect(toList());
    }

    private Coin findCoin(Predicate<Coin> predicate) {
        return getCoinListing()
                .stream()
                .filter(predicate)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException ("crypto currency not found")); //TODO review this error message
    }
}
