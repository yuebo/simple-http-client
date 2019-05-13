package com.eappcat.http.client.es.vo;

import com.eappcat.http.client.es.query.Query;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class QueryRequest {
    private Query query;
    private List<Sort> sort;
    private Integer size;
    private Long from;

    public QueryRequest(){

    }
    public QueryRequest(Query query,List<Sort> sort){
        this(query,sort,10,0L);
    }
    public QueryRequest(Query query,List<Sort> sort,Integer size,Long from){
        this.query=query;
        this.sort=sort;
        this.size=size;
        this.from=from;
    }
}
