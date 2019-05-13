package com.eappcat.http.client.es.vo;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

@Data
public class PageInfo {
    private String scroll="1m";
    @JSONField(name = "scroll_id")
    private String scrollId;
//    private List<Sort> sort;
}
