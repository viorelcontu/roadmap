package com.endava.practice.roadmap.domain.model.enums;

import com.endava.practice.roadmap.domain.model.exceptions.BadRequestException;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.fasterxml.jackson.annotation.JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES;
import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;
import static java.util.Collections.unmodifiableMap;
import static java.util.Optional.ofNullable;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@RequiredArgsConstructor
@Getter
@JsonFormat(shape = STRING, with = ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public enum Currency {

    USD(2781,"United States Dollar"),
    EUR(2790,"Euro"),
    MDL(3555,"Moldovan Leu"),
    RON(2817,"Romanian Leu"),
    RUB(2806,"Russian Ruble"),
    CZK(2788,"Czech Crown");

    private final int externalId;

    private final String title;

    public String getSymbol() {
        return this.name();
    }

    private static final Map<Integer, Currency> externalIdMap = unmodifiableMap(
            Stream.of(Currency.values())
            .collect(toMap(Currency::getExternalId, identity())));

    public static final Map<Currency, String> currencyStringMap = unmodifiableMap(
            Stream.of(Currency.values())
            .collect(Collectors.toMap(identity(), Currency::getTitle)));

    //TODO will be used for quotes in custom currency
    public static Currency fromExternalId (int externalId) {
        return ofNullable(externalIdMap.get(externalId))
                .orElseThrow(() -> new BadRequestException("Currency does not exist"));
    }
}
