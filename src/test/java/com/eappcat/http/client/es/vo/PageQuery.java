package com.eappcat.http.client.es.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class PageQuery  {
    private String scroll="1m";
    @JSONField(name = "scroll_id")
    private String scrollId;
}
