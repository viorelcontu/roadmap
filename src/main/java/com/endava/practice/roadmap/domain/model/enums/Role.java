package com.endava.practice.roadmap.domain.model.enums;

import com.endava.practice.roadmap.domain.model.entities.Group;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.Set;

import static com.endava.practice.roadmap.domain.model.enums.Permission.ACCOUNTING;
import static com.endava.practice.roadmap.domain.model.enums.Permission.AUDIT;
import static com.endava.practice.roadmap.domain.model.enums.Permission.ONBOARDING;
import static com.endava.practice.roadmap.domain.model.enums.Permission.ROOT;
import static com.endava.practice.roadmap.domain.model.enums.Permission.SERVICES;
import static java.util.Collections.unmodifiableMap;
import static java.util.EnumSet.of;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Stream.of;

@RequiredArgsConstructor
@Getter
public enum Role {
    ADMIN (9, of(ROOT, ONBOARDING, ACCOUNTING, AUDIT, SERVICES)),
    MANAGER (7, of(ONBOARDING, ACCOUNTING, AUDIT, SERVICES)),
    ACCOUNTANT (5, of(ACCOUNTING, AUDIT)),
    CLIENT (3, of(SERVICES));

    final private Integer groupId;

    final private Set<Permission> permissions;

    public final static Map<Integer, Role> idRoleMap = unmodifiableMap(
            of(Role.values())
                    .collect(toMap(Role::getGroupId, identity())));

    public Group getEntity () {
        return  Group.builder()
                .id(groupId)
                .role(this)
                .permissions (permissions)
                .build();
    }
}
