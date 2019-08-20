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
    ROOT(8),
    ONBOARDING (6),
    ACCOUNTING (4),
    AUDIT (2),
    SERVICES (0);

    final private Integer permissionId;

    public final static Map<Integer, Permission> idPermissionMap = unmodifiableMap(
            of(Permission.values())
                    .collect(toMap(Permission::getPermissionId, identity())));
}
