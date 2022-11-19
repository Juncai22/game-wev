package com.example.gamewebsearch.service.Impl;

import com.alibaba.fastjson.JSON;
import com.example.common.vo.PrModel;
import com.example.gamewebsearch.config.SearchConfig;
import com.example.gamewebsearch.service.ElsearchProductService;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

@Service
public class ElsearchProductServiceImpl implements ElsearchProductService {

    @Resource
    public RestHighLevelClient restHighLevelClient;

    @Override
    public void up(PrModel prModel) throws IOException {

        //进行索引的名字定义
        IndexRequest indexRequest = new IndexRequest("product");
        //将prModel转化为JSON字符串
        String jsonString = JSON.toJSONString(prModel);
        //进行联系起来
        indexRequest.source(jsonString, XContentType.JSON);
        //开始进行添加
        IndexResponse index = restHighLevelClient.index(indexRequest, SearchConfig.COMMON_OPTIONS);
    }
}
