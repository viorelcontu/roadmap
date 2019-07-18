
package com.endava.practice.roadmap.domain.model.external.responses.quotes;

import com.endava.practice.roadmap.domain.model.external.responses.ExternalStatus;
import lombok.Data;

import java.util.Map;

@Data
public class ExternalQuotesResponse {

    private ExternalStatus status;

    private Map<String, ExternalQuotesData> data;
}
