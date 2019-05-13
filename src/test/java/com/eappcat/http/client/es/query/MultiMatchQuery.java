package com.eappcat.http.client.es.query;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Arrays;

@Data
public class MultiMatchQuery implements Query {
    @JSONField(name = "multi_match")
    private JSONObject multiMatch=new JSONObject();
    public MultiMatchQuery(String [] fields,Object value){
        this.multiMatch.put("query",value);
        this.multiMatch.put("fields", Arrays.asList(fields));
    }
}
