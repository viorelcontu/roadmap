package com.endava.practice.roadmap.domain.service;

import com.endava.practice.roadmap.domain.model.enums.Currency;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Collections.unmodifiableMap;
import static java.util.Collections.unmodifiableSet;
import static java.util.Comparator.comparing;
import static java.util.EnumSet.allOf;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final Map<String, Currency> codeMap;
    private final Map<Integer, Currency> idMap;
    private final Map<Integer, Currency> externalIdMap;

    @Getter
    private final Set<Currency> cryptoSet;

    @Getter
    private final Set<Currency> fiatSet;

    {
        codeMap = unmodifiableMap(allOf(Currency.class).stream()
                .collect(LinkedHashMap::new, (map, crypto) -> map.put(crypto.name(), crypto), Map::putAll));

        idMap = unmodifiableMap(allOf(Currency.class).stream()
                .collect(toMap(Currency::getId, identity())));

        externalIdMap = unmodifiableMap(allOf(Currency.class).stream()
                .collect(toMap(Currency::getExternalId, identity())));

        Comparator<Currency> comparator = comparing(Currency::getId);

        cryptoSet = unmodifiableSet(allOf(Currency.class).stream()
                .filter(Currency::isCrypto)
                .collect(() -> new TreeSet<>(comparator), TreeSet::add, TreeSet::addAll));

        fiatSet = unmodifiableSet(allOf(Currency.class).stream()
                .filter(Currency::isFiat)
                .collect(() -> new TreeSet<>(comparator), TreeSet::add, TreeSet::addAll));
    }

    @Override
    public Currency fromId(int id) {
        return idMap.get(id);
    }

    @Override
    public Currency fromExternalId(int externalId) {
        return externalIdMap.get(externalId);
    }

    @Override
    public Currency fromCode(String code) {
        return codeMap.get(code);
    }

}
