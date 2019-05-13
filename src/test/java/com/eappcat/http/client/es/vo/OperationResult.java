package com.eappcat.http.client.es.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class OperationResult {
    @JSONField(name="_index")
    private String index;
    @JSONField(name="_type")
    private String type;
    @JSONField(name="_id")
    private String id;
    @JSONField(name="_version")
    private Long _version;
    private Boolean created;
    private String result;
    @JSONField(name="_shards")
    private Shard shards;
}
