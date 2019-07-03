package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.model.enums.Currency;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

import static java.util.Collections.unmodifiableMap;
import static java.util.EnumSet.allOf;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Service
public class CurrencyService {

    private final Map <String, Currency> symbolMap;

    @Getter
    private final Map<String, Currency> cryptoMap;

    @Getter
    private final Map<String, Currency> fiatMap;

    private final Map<Integer, Currency> idMap;

    private final Map<Integer, Currency> externalIdMap;

    {
        symbolMap = unmodifiableMap(allOf(Currency.class).stream()
                .collect(LinkedHashMap::new, (map, crypto) -> map.put(crypto.name(), crypto), Map::putAll));

        cryptoMap = unmodifiableMap(allOf(Currency.class).stream()
                .filter(Currency::isCrypto)
                .collect(LinkedHashMap::new, (map, crypto) -> map.put(crypto.name(), crypto), Map::putAll));

        fiatMap = unmodifiableMap(allOf(Currency.class).stream()
                .filter(Currency::isFiat)
                .collect(LinkedHashMap::new, (map, fiat) -> map.put(fiat.name(), fiat), Map::putAll));

        idMap = unmodifiableMap(allOf(Currency.class).stream()
                .collect(toMap(Currency::getId, identity())));

        externalIdMap = unmodifiableMap(allOf(Currency.class).stream()
                .collect(toMap(Currency::getExternalId, identity())));

    }

    public Currency fromId (int id) {
        return idMap.get(id);
    }

    public Currency fromExternalId (int externalId) {
        return externalIdMap.get(externalId);
    }

    public Currency fromSymbol (String symbol) {
        return symbolMap.get(symbol);
    }

}
