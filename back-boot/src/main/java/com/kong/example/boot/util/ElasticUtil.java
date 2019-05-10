package com.kong.example.boot.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
    public static IndexResponse index(IndexRequest indexRequest, RequestOptions options) throws Exception {
        IndexResponse index = null;
        index = restHighLevelClient.index(indexRequest, options);
        return index;
    }


    /**
     * indexAsync
     *
     * @param indexRequest
     * @param options
     * @return
     */
    public static void indexAsync(IndexRequest indexRequest, RequestOptions options) throws Exception {
        restHighLevelClient.indexAsync(indexRequest, RequestOptions.DEFAULT, new ActionListener<IndexResponse>() {
            @Override
            public void onResponse(IndexResponse indexResponse) {
                //当操作成功完成的时候被调用。响应对象以参数的形式传入
                log.info("异步方法成功");
                log.info(JSON.toJSONString(indexResponse));

            }

            @Override
            public void onFailure(Exception e) {
                //故障时被调用。异常对象以参数的形式传入
                log.error("异步方法失败");
            }
        });
    }


    /**
     * get
     *
     * @param getRequest
     * @param requestOptions
     * @return
     */
    public static GetResponse get(GetRequest getRequest, RequestOptions requestOptions) throws Exception {
        GetResponse documentFields = null;
        documentFields = restHighLevelClient.get(getRequest, requestOptions);
        return documentFields;
    }


    /**
     * delete
     *
     * @param request
     * @param requestOptions
     * @return
     * @throws Exception
     */
    public static DeleteResponse delete(DeleteRequest request, RequestOptions requestOptions) throws Exception {
        DeleteResponse delete = restHighLevelClient.delete(request, requestOptions);
        return delete;
    }

    /**
     * update
     *
     * @param request
     * @param requestOptions
     * @return
     * @throws Exception
     */
    public static UpdateResponse update(UpdateRequest request, RequestOptions requestOptions) throws Exception {
        UpdateResponse updateResponse = restHighLevelClient.update(request, requestOptions);
        return updateResponse;
    }
}
