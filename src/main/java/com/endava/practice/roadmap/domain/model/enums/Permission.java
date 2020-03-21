package com.endava.practice.roadmap.domain.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import static java.util.Collections.unmodifiableMap;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Stream.of;

@RequiredArgsConstructor
@Getter
public enum Permission {
    OPERATOR_ADMIN(8),
    CLIENT_ADMIN(6),
    HISTORY(2),
    SERVICES(0);

    private final Integer permissionId;

    public static final Map<Integer, Permission> idPermissionMap = unmodifiableMap(
            of(Permission.values())
            .collect(toMap(Permission::getPermissionId, identity())));
}
