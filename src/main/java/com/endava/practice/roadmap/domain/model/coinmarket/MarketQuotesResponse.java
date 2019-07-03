
package com.endava.practice.roadmap.domain.model.coinmarket;

import lombok.Data;

import java.util.Map;

@Data
public class MarketQuotesResponse {

    private Status status;

    private Map<String, MarketQuotesData> data;
}
