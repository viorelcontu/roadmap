package com.endava.practice.roadmap.util;

import com.endava.practice.roadmap.domain.model.annotations.RequirePermission;
import com.endava.practice.roadmap.domain.model.enums.Permission;
import lombok.NoArgsConstructor;

import java.lang.annotation.Annotation;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor (access = PRIVATE)
public final class TestUtils {

    public static RequirePermission requiredPermissionFactory(final Permission permission) {
        return new RequirePermission() {
            @Override
            public Permission[] value() {
                return new Permission[]{permission};
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return RequirePermission.class;
            }
        };
    }
}
