package com.eappcat.http.client;

import okhttp3.Interceptor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface SimpleClient {
    String baseUrl() default "";
    Class<? extends InterceptorAdapter> interceptor() default InterceptorAdapter.class;
}
