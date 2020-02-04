
package com.endava.practice.roadmap.domain.model.external.quotes;

import com.endava.practice.roadmap.domain.model.external.common.ExternalCoin;
import com.endava.practice.roadmap.domain.model.external.common.ExternalStatus;
import lombok.Data;

import java.util.Map;

@Data
public class ExternalQuotesResponse {

    private ExternalStatus status;

    private Map<String, ExternalCoin> data;
}
