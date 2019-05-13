package com.eappcat.http.client.es.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IndexConfig {
    private String indexName;
    private String type;
//    private String id;
}
