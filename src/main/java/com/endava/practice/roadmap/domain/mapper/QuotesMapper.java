package com.endava.practice.roadmap.domain.mapper;

import com.endava.practice.roadmap.domain.model.enums.Currency;
import com.endava.practice.roadmap.domain.model.external.responses.quotes.ExternalQuotesData;
import com.endava.practice.roadmap.domain.model.external.responses.quotes.ExternalQuotesDataPrice;
import com.endava.practice.roadmap.domain.model.internal.responses.quotes.QuotesPrice;
import com.endava.practice.roadmap.domain.model.internal.responses.quotes.QuotesResponse;
import com.endava.practice.roadmap.domain.service.CurrencyService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class QuotesMapper {

    @Autowired
    protected CurrencyService currencyService;

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    protected abstract QuotesPrice mapQuotesPrice(@Context String code,
                                                  ExternalQuotesDataPrice externalQuotesPrice);

    @AfterMapping
    protected void postMapping(@Context String code, @MappingTarget QuotesPrice quotesPrice) {

        quotesPrice.setCode(code);
        quotesPrice.setId(currencyService.fromCode(code).getId());
    }

    protected List<QuotesPrice> convertQuotesPrice(Map<String, ExternalQuotesDataPrice> quoteMap) {
        return quoteMap.entrySet().stream()
            .map(entry -> mapQuotesPrice(entry.getKey(), entry.getValue()))
            .collect(Collectors.toList());
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(source = "cmcRank", target = "worldRank")
    public abstract QuotesResponse mapExternalQuotesData(ExternalQuotesData exQuotesData);

    @AfterMapping
    protected void postMapping(ExternalQuotesData exQuotesData,
                               @MappingTarget QuotesResponse quotesResponse) {

        Currency currency = currencyService.fromExternalId(exQuotesData.getExternalId());
        quotesResponse.setId(currency.getId());
        quotesResponse.setCode(currency.getCode());
    }
}
