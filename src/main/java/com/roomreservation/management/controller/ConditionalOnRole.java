package com.roomreservation.management.controller;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(ConditionalOnRoleCondition.class)
public @interface ConditionalOnRole {

    String value();
}
