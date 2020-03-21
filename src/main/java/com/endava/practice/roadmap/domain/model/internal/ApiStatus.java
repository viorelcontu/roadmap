package com.endava.practice.roadmap.domain.model.internal;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ApiStatus {

    private final String status;
    private final String message;
    private final String developerMessage;

    @Override
    public String toString() {
        return String.format("Api Error Status: [%s] Reason: %s", status, message);
    }
}
