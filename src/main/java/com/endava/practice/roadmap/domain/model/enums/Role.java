package com.endava.practice.roadmap.domain.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Role {
    ADMIN (9),
    MANAGER (7),
    AUDIT(5),
    CLIENT (3);

    @Getter
    private final Integer groupId;
}
