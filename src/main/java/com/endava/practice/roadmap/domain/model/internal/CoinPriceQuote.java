
package com.endava.practice.roadmap.domain.model.internal;

import com.endava.practice.roadmap.domain.model.enums.Currency;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Getter
@Setter
@EqualsAndHashCode(of = {"currency", "marketCap"})
@JsonInclude(NON_NULL)
@JsonPropertyOrder({
        "currency",
        "price",
        "volume24h",
        "percentChange24h",
        "percentChange7d",
        "marketCap",
        "lastUpdated"})
public class CoinPriceQuote {

    private Currency currency;

    private Double price;

    private Double volume24h;

    private Double percentChange24h;

    private Double percentChange7d;

    private Double marketCap;

    private Instant lastUpdated;
}
