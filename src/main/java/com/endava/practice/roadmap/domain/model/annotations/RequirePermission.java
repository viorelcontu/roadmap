package com.endava.practice.roadmap.domain.model.annotations;

import com.endava.practice.roadmap.domain.model.enums.Permission;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, TYPE})
@Retention(RUNTIME)
public @interface RequirePermission {

    Permission[] value();
}
