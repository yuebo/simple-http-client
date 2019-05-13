package com.eappcat.http.client.es.query;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class MatchQuery implements Query {
    private JSONObject match=new JSONObject();
    public MatchQuery(String field,Object value){
        this.match.put(field,value);
    }
}
