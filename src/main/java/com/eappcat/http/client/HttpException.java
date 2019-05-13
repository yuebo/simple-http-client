package com.eappcat.http.client;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;

@Getter
public class HttpException extends RuntimeException {
    private int code;
    private String body;
    public HttpException(int code, String message,String body) {
        super(message);
        this.code=code;
        this.body=body;
    }

    @Override
    public String toString() {
        return JSONObject.toJSONString(this);
    }
}
