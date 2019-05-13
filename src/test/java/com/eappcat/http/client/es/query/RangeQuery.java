package com.eappcat.http.client.es.query;

import com.alibaba.fastjson.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Arrays;

@Data
public class RangeQuery implements Query {
    private JSONObject range=new JSONObject();
    public RangeQuery(String field,RangeOperator[] operators){
        JSONObject jsonObject=new JSONObject();
        Arrays.asList(operators).forEach(operator->{
            jsonObject.put(operator.operator,operator.value);
        });
        this.range.put(field,jsonObject);

    }
    @AllArgsConstructor
    @Data
    public static class RangeOperator{
        private String operator;
        private Object value;
    }
}
