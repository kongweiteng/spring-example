package com.kong.example.boot.util;


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
        ElasticUtil.restHighLevelClient = restHighLevelClient;
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
//                log.info("异步方法成功");
//                log.info(JSON.toJSONString(indexResponse));

            }

            @Override
            public void onFailure(Exception e) {
                //故障时被调用。异常对象以参数的形式传入
                log.error("ElasticUtil indexAsync file : {}" + e.getMessage());
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

    public static void updateAsync(UpdateRequest request, RequestOptions requestOptions) throws Exception {
        restHighLevelClient.updateAsync(request, requestOptions, new ActionListener<UpdateResponse>() {
            @Override
            public void onResponse(UpdateResponse updateResponse) {
                log.info("update ok");
            }

            @Override
            public void onFailure(Exception e) {
                log.error("ElasticUtil updateAsync file : {}" + e.getMessage());
            }
        });
    }


    /**
     * search
     *
     * @param index
     * @param requestOptions
     * @throws Exception
     */
//    public static <T> IPage<T> search(String index, PageReq pageReq, RequestOptions requestOptions, Class<T> tClass) throws Exception {
//        IPage page = new Page();
//        long from = (pageReq.getCurrent() - 1) * pageReq.getSize();
//        long size = pageReq.getSize();
//
//        SearchRequest searchRequest = new SearchRequest(index);
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        //条件查询
//        if (StringUtils.isNotBlank(pageReq.getConditonMap())) {
//            HashMap<String, String> stringObjectMap = JSON.parseObject(pageReq.getConditonMap(), HashMap.class);
//            stringObjectMap.entrySet().forEach(e -> {
//                if (StringUtils.isNotBlank(e.getKey()) && StringUtils.isNotBlank(e.getValue())) {
//                    searchSourceBuilder.query(QueryBuilders.matchPhraseQuery(e.getKey(), e.getValue()));
//                }
//            });
//        }
//
//
//        //模糊查询
//        if (StringUtils.isNotBlank(pageReq.getLikeMap())) {
//            HashMap<String, String> likeMap = JSON.parseObject(pageReq.getLikeMap(), HashMap.class);
//            likeMap.entrySet().forEach(e -> {
//                if (StringUtils.isNotBlank(e.getKey()) && e.getValue() != null) {
//                    searchSourceBuilder.query(QueryBuilders.matchQuery(e.getKey(), e.getValue()).fuzziness(Fuzziness.AUTO));
//                }
//            });
//        }
//
//        //时间范围查询
//
//        if (StringUtils.isNotBlank(pageReq.getStartTime())) {
//            Long end = System.currentTimeMillis();
//            if (StringUtils.isNotBlank(pageReq.getEndTime())) {
//                end = DateUtil.parse(pageReq.getEndTime()).getTime();
//            }
//            searchSourceBuilder.query(QueryBuilders.rangeQuery("timeStamp").gte(DateUtil.parse(pageReq.getStartTime()).getTime()).lte(end));
//        }
//
//
//        //设置排序
//        SortOrder desc = SortOrder.DESC;
//        if (pageReq.getOrderDesc() != null && !pageReq.getOrderDesc()) {
//            desc = SortOrder.ASC;
//        }
//        searchSourceBuilder.sort(new FieldSortBuilder(pageReq.getOrderBy()).order(desc));
//
//
//        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//
//        //根据页数设置查询的条数
//        searchSourceBuilder.from((int) from);
//        searchSourceBuilder.size((int) size);
//
//        searchRequest.source(searchSourceBuilder);
//
//        SearchResponse search = restHighLevelClient.search(searchRequest, requestOptions);
//        SearchHits hits = search.getHits();
//
//        long totalHits = hits.totalHits;
//
//        List<T> collect = Arrays.stream(hits.getHits()).map(e -> {
//            String sourceAsString = e.getSourceAsString();
//            T t1 = JSON.parseObject(sourceAsString, tClass);
//            return t1;
//        }).collect(Collectors.toList());
//
//        page.setRecords(collect);
//        page.setCurrent(pageReq.getCurrent());
//        page.setSize(pageReq.getSize());
//        page.setTotal(totalHits);
//        return page;
//
//
//    }
}
