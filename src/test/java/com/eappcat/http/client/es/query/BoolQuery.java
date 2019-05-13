package com.eappcat.http.client.es.query;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Arrays;

@Data
public class BoolQuery implements Query {
    private JSONObject bool=new JSONObject();
    public BoolQuery(){

    }
    public BoolQuery must(Query query){
        this.bool.put("must",query);
        return this;
    }

    public BoolQuery mustNot(Query query){
        this.bool.put("must_not",query);
        return this;
    }
    public BoolQuery should(Query[] query){
        this.bool.put("should", Arrays.asList(query));
        return this;
    }
    public BoolQuery filter(Query query){
        this.bool.put("filter", query);
        return this;
    }
}
