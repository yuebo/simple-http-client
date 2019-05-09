package com.eappcat.http.client;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

public class InterceptorAdapter implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
