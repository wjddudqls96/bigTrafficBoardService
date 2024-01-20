package com.example.boardserver.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 런타인시 실행하도록 하는 어노테이션
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LoginCheck {

    public static enum UserType {
        USER, ADMIN;
    }

    UserType type();
}
