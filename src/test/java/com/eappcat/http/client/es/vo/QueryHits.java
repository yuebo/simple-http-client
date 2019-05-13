package com.eappcat.http.client.es.vo;

import lombok.Data;

import java.util.List;

@Data
public class QueryHits<T> {
    private String total;
    private String max_score;
    private List<Hit<T>> hits;
}
