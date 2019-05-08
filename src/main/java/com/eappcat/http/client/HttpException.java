package com.eappcat.http.client;

import lombok.Getter;

@Getter
public class HttpException extends RuntimeException {
    private int code;
    public HttpException(int code, String message) {
        super(message);
        this.code=code;
    }
}
