
package com.endava.practice.roadmap.domain.model.external.common;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class ExternalStatus {

    private Instant timestamp;

    @JsonProperty("error_code")
    private Integer errorCode;

    @JsonProperty("error_message")
    private String errorMessage;

    private Integer elapsed;

    @JsonProperty("credit_count")
    private Integer creditCount;
}
