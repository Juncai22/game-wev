package com.example.gamewebsearch.config;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchConfig {

    public static final RequestOptions COMMON_OPTIONS;

    //进行叠加的控制client
    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();

        COMMON_OPTIONS = builder.build();
    }

    //进行叠加的控制client
    @Bean
    public RestHighLevelClient esRestClient(){

        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.110.132",9200,"http")));
    }
}
