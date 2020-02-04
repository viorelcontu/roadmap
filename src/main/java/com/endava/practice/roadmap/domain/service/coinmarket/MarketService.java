package com.endava.practice.roadmap.domain.service.coinmarket;

import com.endava.practice.roadmap.domain.model.annotations.RequirePermission;
import com.endava.practice.roadmap.domain.model.enums.Currency;
import com.endava.practice.roadmap.domain.model.internal.Coin;

import java.util.List;
import java.util.Map;

import static com.endava.practice.roadmap.domain.model.enums.Permission.SERVICES;

public interface MarketService {

    Coin getQuotes(final String symbol);

    @RequirePermission(SERVICES)
    List<Coin> getListing();

    @RequirePermission(SERVICES)
    Map<Currency, String> getCurrencies();
}
