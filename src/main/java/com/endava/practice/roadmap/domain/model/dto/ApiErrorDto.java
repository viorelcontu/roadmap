package com.endava.practice.roadmap.domain.model.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ApiErrorDto {

    final private String status;
    final private String message;
    final private String developerMessage;

    @Override
    public String toString() {
        return String.format("Api Error Status: [%s] Reason: %s", status, message);
    }
}
