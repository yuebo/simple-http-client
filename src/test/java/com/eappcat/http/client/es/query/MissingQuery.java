package com.eappcat.http.client.es.query;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class MissingQuery implements Query {
    private JSONObject missing=new JSONObject();
    public MissingQuery(String field){
        this.missing.put("field",field);
    }
}
