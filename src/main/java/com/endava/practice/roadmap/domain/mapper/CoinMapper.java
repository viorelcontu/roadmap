package com.endava.practice.roadmap.domain.mapper;

import com.endava.practice.roadmap.domain.model.enums.Currency;
import com.endava.practice.roadmap.domain.model.external.common.ExternalCoin;
import com.endava.practice.roadmap.domain.model.external.common.ExternalCoinPriceQuote;
import com.endava.practice.roadmap.domain.model.internal.Coin;
import com.endava.practice.roadmap.domain.model.internal.CoinPriceQuote;
import org.mapstruct.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface CoinMapper {

    default List<CoinPriceQuote> convertQuotesPrice(Map<String, ExternalCoinPriceQuote> quoteMap) {
        return quoteMap.entrySet().stream()
                .map(entry -> mapCoinPriceQuote(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    @Mapping(target = "currency", ignore = true)
    CoinPriceQuote mapCoinPriceQuote(@Context String currencySymbol,
                                     ExternalCoinPriceQuote externalQuotesPrice);

    @AfterMapping
    default void postMapping(@Context String currencySymbol, @MappingTarget CoinPriceQuote coinPriceQuote) {
        coinPriceQuote.setCurrency(Currency.valueOf(currencySymbol));
    }

    @Mapping(target = "title", source = "name")
    @Mapping(target = "codeName", source = "slug")
    @Mapping(target = "worldRank", source = "cmcRank")
    Coin mapCoinAndQuotes(ExternalCoin externalCoin);

    @InheritConfiguration
    @Mapping(target = "quote", ignore = true)
    Coin mapCoinListing(ExternalCoin externalCoin);
}
