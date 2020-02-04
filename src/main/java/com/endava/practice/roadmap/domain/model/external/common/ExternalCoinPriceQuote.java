
package com.endava.practice.roadmap.domain.model.external.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ExternalCoinPriceQuote {

    private Double price;

    @JsonProperty("volume_24h")
    private Double volume24h;

    @JsonProperty("percent_change_1h")
    private Double percentChange1h;

    @JsonProperty("percent_change_24h")
    private Double percentChange24h;

    @JsonProperty("percent_change_7d")
    private Double percentChange7d;

    @JsonProperty("market_cap")
    private Double marketCap;

    @JsonProperty("last_updated")
    private Instant lastUpdated;
}
