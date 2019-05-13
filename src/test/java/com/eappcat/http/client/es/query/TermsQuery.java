package com.eappcat.http.client.es.query;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Arrays;

@Data
public class TermsQuery implements Query {
    private JSONObject terms=new JSONObject();
    public TermsQuery(String field,Object [] values){
        this.terms.put(field, Arrays.asList(values));
    }
}
