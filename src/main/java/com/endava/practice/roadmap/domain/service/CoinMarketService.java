package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.mapper.QuotesMapper;
import com.endava.practice.roadmap.domain.model.coinmarket.MarketQuotesResponse;
import com.endava.practice.roadmap.domain.model.enums.Currency;
import com.endava.practice.roadmap.domain.model.quotes.LocalQuotesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

import static com.endava.practice.roadmap.domain.exception.BadRequestException.ofWrongCrypto;
import static com.endava.practice.roadmap.domain.model.enums.CoinMarketApi.QUOTES;
import static java.util.Optional.ofNullable;
import static org.springframework.http.RequestEntity.get;

@RequiredArgsConstructor
@Service
@Profile({"dev", "prod"})
public class CoinMarketService implements MarketService {

    private final RestTemplate coinMarketRestClient;

    private final QuotesMapper quotesMapper;

    private final CurrencyService currencyService;

    @Override
    public LocalQuotesResponse getQuotes(final int id) {

        Optional<Integer> externalId = ofNullable(currencyService.fromId(id))
                .filter(Currency::isCrypto)
                .map(Currency::getExternalId);

        return requestMarketQuotes(externalId.orElseThrow(() -> ofWrongCrypto(id)));
    }

    @Override
    public LocalQuotesResponse getQuotes(final String symbol) {

        Optional<Integer> externalId = ofNullable(currencyService.fromSymbol(symbol))
                .filter(Currency::isCrypto)
                .map(Currency::getExternalId);

        return requestMarketQuotes(externalId.orElseThrow(() -> ofWrongCrypto(symbol)));
    }

    private LocalQuotesResponse requestMarketQuotes(int id) {

        URI requestUri = QUOTES.buildUri("id={id}").expand(id).encode().toUri();

        RequestEntity<?> request = get(requestUri).build();
        ResponseEntity<MarketQuotesResponse> response = coinMarketRestClient.exchange(request, MarketQuotesResponse.class);
        return quotesMapper.convertToLocalQuotesResponse(response.getBody());
    }
}
