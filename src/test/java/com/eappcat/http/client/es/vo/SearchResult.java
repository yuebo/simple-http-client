package com.eappcat.http.client.es.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class SearchResult<T> {
    @JSONField(name = "_shards")
    public Shard shards;
    private Long took;
    private QueryHits<T> hits;
    @JSONField(name = "_scroll_id")
    private String scrollId;
    @JSONField(name = "timed_out")
    private Boolean timeOut;
}
