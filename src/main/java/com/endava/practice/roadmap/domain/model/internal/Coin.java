
package com.endava.practice.roadmap.domain.model.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@EqualsAndHashCode(of = "symbol")
@Getter
@Setter
@ToString (includeFieldNames = false, of = "symbol")
@JsonPropertyOrder ({
        "title",
        "symbol",
        "codeName",
        "worldRank",
        "quote"})
@JsonInclude(NON_NULL)
public class Coin implements Serializable {

    private String title;

    private String symbol;

    private String codeName;

    @JsonIgnore
    private Integer externalId;

    private Integer worldRank;

    private List<CoinPriceQuote> quote;
}
