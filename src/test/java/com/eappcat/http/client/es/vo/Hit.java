package com.eappcat.http.client.es.vo;

import lombok.Data;

@Data
public class Hit<T> {
    private String _index;
    private String _type;
    private String _id;
    private Double _score;
    private T _source;
}
