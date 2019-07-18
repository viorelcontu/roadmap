
package com.endava.practice.roadmap.domain.model.internal.responses.quotes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonPropertyOrder ({
        "code",
        "id",
        "name",
        "worldRank",
        "lastUpdated",
        "quote"})
@JsonInclude(NON_NULL)
public class QuotesResponse {

    private String code;

    private Integer id;

    private String name;

    private Integer worldRank;

    private Instant lastUpdated;

    private List<QuotesPrice> quote;
    }
