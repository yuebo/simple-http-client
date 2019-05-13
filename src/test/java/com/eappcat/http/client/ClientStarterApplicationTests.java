package com.eappcat.http.client;

import com.alibaba.fastjson.JSONObject;
import com.eappcat.http.client.es.DocumentRestClient;
import com.eappcat.http.client.es.query.ExistsQuery;
import com.eappcat.http.client.es.query.MatchAllQuery;
import com.eappcat.http.client.es.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ClientStarterApplicationTests {

    @Autowired
    DocumentRestClient restClient;

    @Test
    public void contextLoads() {
//        log.info("{}",testClient.test("123"));
        JSONObject doc=new JSONObject();
        doc.put("user","kimchy");
        doc.put("post_date","2009-11-15T14:12:12");
        doc.put("message","trying out Elasticsearch");
        doc.put("create_date",new Date());

//        JSONObject index=indexClient.addIndex("twitter");
        IndexConfig config=IndexConfig.builder().indexName("twitter").type("tweet").build();
//        OperationResult result=indexClient.putDocument(config,doc);
//        log.info("{}",result.get_shards());
//        OperationResult result2=indexClient.updateDocument(config,result.get_id(),doc);
//        log.info("{}",result2.get_shards());

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("size",3);
        PageInfo pageInfo =new PageInfo();
//        pageInfo.setSort(Arrays.asList(new Sort[]{new Sort("user","desc")}));
//        jsonObject.put("sort",Arrays.asList(new Sort[]{new Sort("user.keyword",new Order("desc"))}));
        SearchResult<JSONObject> searchResult=restClient.searchScroll(config, pageInfo,new QueryRequest(new ExistsQuery("create_date"),Arrays.asList(new Sort[]{new Sort().appendKeyword("user",new Order("desc"))}),3),JSONObject.class);

        log.info("{}",searchResult);

        PageInfo next=new PageInfo();
        next.setScrollId(searchResult.getScrollId());
        SearchResult<JSONObject> page2=restClient.nextScroll(next,JSONObject.class);

        log.info("{}",page2);

    }

}
