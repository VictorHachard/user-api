package com.user.interceptor;

import com.user.model.entities.enums.RoleEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthorisationForOverride {
    /**
     * The name of the methode.
     */
    String name();
    RoleEnum[] roles();
}
