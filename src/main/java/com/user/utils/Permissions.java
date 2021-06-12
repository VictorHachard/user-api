package com.user.utils;

import com.user.model.entities.enums.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permissions {
    RoleEnum[] roleList() default {RoleEnum.ROLE_USER};
}
