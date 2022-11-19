package com.example.gamewebsearch;

import com.alibaba.fastjson.JSON;
import com.example.gamewebsearch.config.SearchConfig;
import lombok.Data;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Collections;

@SpringBootTest
@RunWith(SpringRunner.class)
public class GameWebSearchApplicationTests {

    @Resource
    public RestHighLevelClient restHighLevelClient;

    @Test
    public void contextLoads() {
        System.out.println(restHighLevelClient);
    }

    @Test
    public void indexTest() throws IOException {
        IndexRequest indexRequest = new IndexRequest("users");
        User user = new User();
        user.setAge(1);
        user.setUsarName("Juncai");
        String jsonString = JSON.toJSONString(user);
        indexRequest.source(jsonString, XContentType.JSON);


        IndexResponse index = restHighLevelClient.index(indexRequest, SearchConfig.COMMON_OPTIONS);

        System.out.println(index);
    }


    @Test
    public void searchIndex() throws IOException {
        SearchRequest searchRequest = new SearchRequest();

        searchRequest.indices("newbank");
        SearchSourceBuilder requestBuilder = new SearchSourceBuilder();

        requestBuilder.query(QueryBuilders.matchQuery("address", "court"));
        requestBuilder.aggregation(AggregationBuilders.terms("ageAgg").field("age").size(10));

        searchRequest.source(requestBuilder);

        SearchResponse search = restHighLevelClient.search(searchRequest, SearchConfig.COMMON_OPTIONS);

        System.out.println(search);
    }


    class User {
        private String usarName;
        private Integer age;

        public String getUsarName() {
            return usarName;
        }

        public void setUsarName(String usarName) {
            this.usarName = usarName;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }
    }
}
