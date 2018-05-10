package com.example.xiaomi.test.API_for_server.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// classes, methods which return json response
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Responsible {
    public final String SIMPLE = "simple";
    public final String ARRAY = "array";
    public boolean responsible() default true;
    public String type() default SIMPLE;
}
