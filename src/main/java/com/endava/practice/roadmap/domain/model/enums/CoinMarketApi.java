package com.endava.practice.roadmap.domain.model.enums;

import lombok.RequiredArgsConstructor;
import org.springframework.web.util.UriComponents;

import static org.springframework.web.util.UriComponentsBuilder.newInstance;

@RequiredArgsConstructor
public enum CoinMarketApi {

    MAPPING("map"),
    METADATA("info"),
    LISTING("listings/latest"),
    LISTING_HISTORY("listings/historical"),
    QUOTES("quotes/latest"),
    QUOTES_HISTORY("quotes/historical");

    private static String apiPrefix = "/v1/cryptocurrency/";

    private final String apiPath;

    public UriComponents buildUri(final String query) {
        return newInstance().scheme("https").path(apiPrefix + apiPath).query(query).build();
    }
}
