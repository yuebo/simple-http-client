package com.eappcat.http.client;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface SimpleClient {
    String baseUrl() default "";
}
