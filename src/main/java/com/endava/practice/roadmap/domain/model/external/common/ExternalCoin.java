
package com.endava.practice.roadmap.domain.model.external.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;
import java.util.EnumSet;
import java.util.Map;

@Data
public class ExternalCoin {

    @JsonProperty ("id")
    private Integer externalId;

    private String name;

    private String symbol;

    private String slug;

    @JsonProperty("cmc_rank")
    private Integer cmcRank;

    @JsonProperty("num_market_pairs")
    private Integer numMarketPairs;

    @JsonProperty("circulating_supply")
    private Long circulatingSupply;

    @JsonProperty("total_supply")
    private Long totalSupply;

    @JsonProperty("max_supply")
    private Long maxSupply;

    @JsonProperty("date_added")
    private Instant dateAdded;

    private EnumSet<Tag> tags;

    private Platform platform;

    @JsonProperty("last_updated")
    private Instant lastUpdated;

    private Map<String, ExternalCoinPriceQuote> quote;
}
