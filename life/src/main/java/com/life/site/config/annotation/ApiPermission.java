package com.life.site.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.METHOD, ElementType.TYPE}) // 클래스와 메소드 모두 사용
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiPermission {
    Role [] role() default Role.VENDOR;
    enum Role{
        ADMIN,  //관리자
        MEMBER,   //유저
        VENDOR, //벤더
        NONE,  //권한 없음
    }
}
