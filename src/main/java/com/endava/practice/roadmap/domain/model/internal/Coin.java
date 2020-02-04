
package com.endava.practice.roadmap.domain.model.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@EqualsAndHashCode(of = "symbol")
@Getter
@Setter
@JsonPropertyOrder ({
        "title",
        "symbol",
        "codeName",
        "worldRank",
        "quote"})
@JsonInclude(NON_NULL)
public class Coin {

    private String title;

    private String symbol;

    private String codeName;

    @JsonIgnore
    private Integer externalId;

    private Integer worldRank;

    private List<CoinPriceQuote> quote;
}
