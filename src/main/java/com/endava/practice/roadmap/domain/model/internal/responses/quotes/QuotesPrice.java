
package com.endava.practice.roadmap.domain.model.internal.responses.quotes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonInclude(NON_NULL)
@JsonPropertyOrder({
        "code",
        "id",
        "price",
        "volume24h",
        "percentChange1h",
        "percentChange24h",
        "percentChange7d",
        "marketCap"})
public class QuotesPrice {

    private String code;

    private Integer id;

    private Double price;

    private Double volume24h;

    private Double percentChange1h;

    private Double percentChange24h;

    private Double percentChange7d;

    private Double marketCap;
}
