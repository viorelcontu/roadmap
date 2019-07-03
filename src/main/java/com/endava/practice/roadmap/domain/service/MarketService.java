package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.model.quotes.LocalQuotesResponse;

public interface MarketService {

    LocalQuotesResponse getQuotes(final int symbol);

    LocalQuotesResponse getQuotes(final String symbol);
}
