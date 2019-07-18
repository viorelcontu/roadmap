package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.model.internal.responses.quotes.QuotesResponse;

public interface MarketService {

    QuotesResponse getQuotes(final int symbol);

    QuotesResponse getQuotes(final String symbol);
}
