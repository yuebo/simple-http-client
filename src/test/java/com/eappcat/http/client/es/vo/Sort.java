package com.eappcat.http.client.es.vo;

import lombok.NoArgsConstructor;

import java.util.HashMap;

@NoArgsConstructor
public class Sort extends HashMap<String,Order> {
    public Sort appendField(String field,Order order){
        this.put(field,order);
        return this;
    }
    public Sort appendKeyword(String field,Order order){
        this.put(field.concat(".keyword"),order);
        return this;
    }
}
