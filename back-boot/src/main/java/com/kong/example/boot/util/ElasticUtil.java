package com.kong.example.boot.util;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class ElasticUtil {
    private static RestHighLevelClient restHighLevelClient;

    @Autowired
    public void setRestHighLevelClient(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }


    /**
     * index
     * put data, edit data ,when index is not , can creat
     *
     * @param indexRequest
     * @param options
     * @return
     */
    public static IndexResponse index(IndexRequest indexRequest, RequestOptions options) {
        IndexResponse index = null;
        try {
            index = restHighLevelClient.index(indexRequest, options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return index;
    }

}
