
package com.endava.practice.roadmap.domain.model.quotes;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.Instant;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@JsonPropertyOrder ({
        "id",
        "name",
        "worldRank",
        "lastUpdated",
        "quote"
})
@JsonInclude(NON_NULL)
public class LocalQuotesData {

    private Integer Id;

    private String name;

    private Integer worldRank;

    private Instant lastUpdated;

    private Map<String, LocalQuote> quote;
}
