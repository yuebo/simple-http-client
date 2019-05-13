package com.eappcat.http.client.es;

import com.alibaba.fastjson.JSONObject;
import com.eappcat.http.client.SimpleClient;
import com.eappcat.http.client.es.vo.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@SimpleClient(baseUrl = "http://localhost:9200")
public interface DocumentRestClient {
//    @RequestMapping(value = "/{indexName}",method = RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    JSONObject addIndex(@PathVariable("indexName") String indexName);
//

    @RequestMapping(value = "/{indexName}/{type}",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    OperationResult putDocument(@PathVariable IndexConfig indexConfig, @RequestBody JSONObject body);

    @RequestMapping(value = "/{indexName}/{type}/{id}",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    OperationResult updateDocument(@PathVariable IndexConfig indexConfig, @PathVariable("id")String id, @RequestBody JSONObject body);

    @RequestMapping(value = "/{indexName}/{type}/{id}",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    OperationResult deleteDocument(@PathVariable IndexConfig indexConfig, @PathVariable("id")String id);

    @RequestMapping(value = "/{indexName}/{type}/_search",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    <T> SearchResult<T> search(@PathVariable IndexConfig indexConfig, @RequestBody QueryRequest body, Class<T> resultClass);

    @RequestMapping(value = "/{indexName}/{type}/_search",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    <T> SearchResult<T> searchScroll(@PathVariable IndexConfig indexConfig, @RequestParam PageInfo scroll, @RequestBody QueryRequest body, Class<T> resultClass);

    @RequestMapping(value = "/_search/scroll",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    <T> SearchResult<T> nextScroll(@RequestBody Object body,Class<T> resultClass);

}
