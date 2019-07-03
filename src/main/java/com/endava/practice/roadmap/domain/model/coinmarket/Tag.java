package com.endava.practice.roadmap.domain.model.coinmarket;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Tag {
    MINEABLE ("mineable");

    @JsonValue
    final private String value;
}
