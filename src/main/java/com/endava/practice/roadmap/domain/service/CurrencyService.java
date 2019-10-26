package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.model.enums.Currency;

import java.util.Set;

public interface CurrencyService {
    Currency fromId(int id);

    Currency fromExternalId(int externalId);

    Currency fromCode(String code);

    Set<Currency> getCryptoSet();

    Set<Currency> getFiatSet();
}
