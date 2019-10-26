package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.mapper.QuotesMapper;
import com.endava.practice.roadmap.domain.model.annotations.RequirePermission;
import com.endava.practice.roadmap.domain.model.enums.Currency;
import com.endava.practice.roadmap.domain.model.enums.Permission;
import com.endava.practice.roadmap.domain.model.external.responses.quotes.ExternalQuotesData;
import com.endava.practice.roadmap.domain.model.external.responses.quotes.ExternalQuotesResponse;
import com.endava.practice.roadmap.domain.model.internal.responses.quotes.QuotesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.endava.practice.roadmap.domain.model.enums.Permission.SERVICES;
import static com.endava.practice.roadmap.domain.model.exceptions.BadRequestException.ofWrongCrypto;
import static com.endava.practice.roadmap.domain.model.enums.CoinMarketApi.QUOTES;
import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Service
public class MarketServiceImpl implements MarketService {

    private final RestTemplate coinMarketRestClient;

    private final QuotesMapper quotesMapper;

    private final CurrencyService currencyService;

    @Override
    @RequirePermission(SERVICES)
    public QuotesResponse getQuotes(final int id) {

        Optional<Integer> externalId = ofNullable(currencyService.fromId(id))
                .filter(Currency::isCrypto)
                .map(Currency::getExternalId);

        return requestMarketQuotes(externalId.orElseThrow(() -> ofWrongCrypto(id)));
    }

    @Override
    @RequirePermission(SERVICES)
    public QuotesResponse getQuotes(final String symbol) {

        Optional<Integer> externalId = ofNullable(currencyService.fromCode(symbol))
                .filter(Currency::isCrypto)
                .map(Currency::getExternalId);

        return requestMarketQuotes(externalId.orElseThrow(() -> ofWrongCrypto(symbol)));
    }

    private QuotesResponse requestMarketQuotes(int id) {

        String requestUrl = QUOTES.buildUri("id={id}").expand(id).encode().toString();
        ResponseEntity<ExternalQuotesResponse> response = coinMarketRestClient.getForEntity(requestUrl, ExternalQuotesResponse.class);

        ExternalQuotesData exQuotesData = ofNullable(response.getBody())
            .map(exQuotesResponse -> exQuotesResponse.getData().get(valueOf(id)))
            .orElseThrow(() -> new HttpServerErrorException (HttpStatus.INTERNAL_SERVER_ERROR));

        return quotesMapper.mapExternalQuotesData(exQuotesData);
    }
}
