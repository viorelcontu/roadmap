package com.endava.practice.roadmap.domain.mapper;

import com.endava.practice.roadmap.domain.model.coinmarket.MarketQuote;
import com.endava.practice.roadmap.domain.model.coinmarket.MarketQuotesData;
import com.endava.practice.roadmap.domain.model.coinmarket.MarketQuotesResponse;
import com.endava.practice.roadmap.domain.model.quotes.LocalQuote;
import com.endava.practice.roadmap.domain.model.quotes.LocalQuotesData;
import com.endava.practice.roadmap.domain.model.quotes.LocalQuotesResponse;
import com.endava.practice.roadmap.domain.service.CurrencyService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.stream.Collectors;

import static java.util.function.Function.identity;

@Mapper
public abstract class QuotesMapper {

    @Autowired
    protected CurrencyService currencyService;

    public abstract LocalQuotesResponse convertToLocalQuotesResponse(MarketQuotesResponse marketQuotesResponse);

    @Mapping(target = "id", expression = "java( currencyService.fromExternalId(marketQuotesData.getExternalId()).getId() )")
    @Mapping(source = "cmcRank", target = "worldRank")
    public abstract LocalQuotesData convertMarketQuotesData(MarketQuotesData marketQuotesData);

    protected Map<String, LocalQuotesData> convertQuotesDataKeys(Map<String, MarketQuotesData> coinMap) {
        return coinMap.values().stream()
                .map (this::convertMarketQuotesData)
                .collect(Collectors.toMap(quotesData -> currencyService.fromId(quotesData.getId()).name(), identity()));
    }

    @Mapping( target = "id", ignore = true)
    protected abstract LocalQuote convertMarketQuote (MarketQuote marketQuote);

    protected LocalQuote convertMarketQuote (MarketQuote marketQuote, String symbol) {
        LocalQuote localQuote = convertMarketQuote(marketQuote);
        localQuote.setId(currencyService.fromSymbol(symbol).getId());
        return localQuote;
    }

    protected  Map<String, LocalQuote>  convertQuotesKeys (Map<String, MarketQuote> quoteMap) {
        return quoteMap.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> convertMarketQuote(entry.getValue(), entry.getKey())));
    }
}
