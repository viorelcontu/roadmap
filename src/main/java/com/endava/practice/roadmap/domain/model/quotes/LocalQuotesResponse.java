
package com.endava.practice.roadmap.domain.model.quotes;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Setter
@EqualsAndHashCode
@ToString
@JsonInclude(NON_NULL)
public class LocalQuotesResponse {

    @JsonAnyGetter
    public Map<String, LocalQuotesData> getData() {
        return data;
    }

    private Map<String, LocalQuotesData> data;
}
