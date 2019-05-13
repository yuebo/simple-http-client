package com.eappcat.http.client.es.query;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class TermQuery implements Query {
    private JSONObject term=new JSONObject();
    public TermQuery(String field,Object o){
        term.put(field,o);
    }
}
