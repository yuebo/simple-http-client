package com.eappcat.http.client.es.vo;

import lombok.Data;

@Data
public class Shard {
    private Long total;
    private Long failed;
    private Long successful;
    private Long skipped;
}
