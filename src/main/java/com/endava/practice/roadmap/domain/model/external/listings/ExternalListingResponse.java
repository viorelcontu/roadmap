
package com.endava.practice.roadmap.domain.model.external.listings;

import com.endava.practice.roadmap.domain.model.external.common.ExternalStatus;
import com.endava.practice.roadmap.domain.model.external.common.ExternalCoin;
import lombok.Data;

import java.util.List;

@Data
public class ExternalListingResponse {

    private ExternalStatus status;

    private List<ExternalCoin> data;
}
