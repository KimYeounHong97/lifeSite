package com.life.site.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = {ElementType.PARAMETER}) // 클래스와 메소드 모두 사용
@Retention(RetentionPolicy.RUNTIME)
public @interface UserSession {

}
