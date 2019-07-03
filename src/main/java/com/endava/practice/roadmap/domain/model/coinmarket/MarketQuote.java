
package com.endava.practice.roadmap.domain.model.coinmarket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class MarketQuote {

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
