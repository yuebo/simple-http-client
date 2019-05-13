package com.eappcat.http.client.es.query;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class ExistsQuery implements Query {
    private JSONObject exists=new JSONObject();
    public ExistsQuery(String field){
        this.exists.put("field",field);
    }
}
