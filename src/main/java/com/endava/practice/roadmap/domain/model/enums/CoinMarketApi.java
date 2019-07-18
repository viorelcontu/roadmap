package com.endava.practice.roadmap.domain.model.enums;

import org.springframework.web.util.UriComponents;

import static org.springframework.web.util.UriComponentsBuilder.newInstance;

public enum CoinMarketApi {

    MAPPING("map"),
    METADATA("info"),
    LISTING("listings/latest"),
    LISTING_HISTORY("listings/historical"),
    QUOTES("quotes/latest"),
    QUOTES_HISTORY("quotes/historical");

    private final String path;

    CoinMarketApi(String apiPath) {
        String apiPrefix = "/v1/cryptocurrency/";
        this.path = apiPrefix + apiPath;
    }

    public UriComponents buildUri(final String query) {
        return newInstance().path(path).query(query).build();
    }
}
