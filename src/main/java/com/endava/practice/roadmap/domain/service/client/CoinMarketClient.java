package com.endava.practice.roadmap.domain.service.client;

import com.endava.practice.roadmap.domain.model.external.common.ExternalCoin;
import com.endava.practice.roadmap.domain.model.external.listings.ExternalListingResponse;
import com.endava.practice.roadmap.domain.model.external.quotes.ExternalQuotesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.function.Supplier;

import static com.endava.practice.roadmap.domain.model.enums.CoinMarketApi.LISTING;
import static com.endava.practice.roadmap.domain.model.enums.CoinMarketApi.QUOTES;
import static java.lang.String.valueOf;
import static java.util.Optional.ofNullable;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

//TODO test with @RestClientTest
@Component
@RequiredArgsConstructor
public class CoinMarketClient {

    private static final Supplier<HttpServerErrorException> INTERNAL_SERVER_ERROR_EXCEPTION =
            () -> new HttpServerErrorException(INTERNAL_SERVER_ERROR);

    private final RestTemplate coinMarketRestClient;

    public ExternalCoin requestMarketQuotes(final int externalId) {
        final String requestUrl = QUOTES.buildUri("id={id}").expand(externalId).encode().toString();

        ResponseEntity<ExternalQuotesResponse> response = coinMarketRestClient
                .getForEntity(requestUrl, ExternalQuotesResponse.class);

        return ofNullable(response.getBody())
                .map(exQuotesResponse -> exQuotesResponse.getData().get(valueOf(externalId)))
                .orElseThrow(INTERNAL_SERVER_ERROR_EXCEPTION);
    }

    public List<ExternalCoin> requestCoinListing(final int limit) {
        final MultiValueMap<String, String> queryMap = buildQueryParams(limit);
        final String requestUrl = LISTING.buildUri(queryMap).encode().toString();

        ResponseEntity<ExternalListingResponse> response = coinMarketRestClient
                .getForEntity(requestUrl, ExternalListingResponse.class);

        return ofNullable(response.getBody())
                .map(ExternalListingResponse::getData)
                .orElseThrow(INTERNAL_SERVER_ERROR_EXCEPTION);
    }

    private MultiValueMap<String, String> buildQueryParams(int limit) {
        final var queryMap = new LinkedMultiValueMap<String, String>();
        queryMap.add("limit",Integer.toString(limit));
        queryMap.add("sort", "market_cap");
        queryMap.add("sort_dir", "desc");
        return queryMap;
    }
}
