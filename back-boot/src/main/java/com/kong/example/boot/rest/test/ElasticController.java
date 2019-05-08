package com.kong.example.boot.rest.test;

import com.alibaba.fastjson.JSON;
import com.kong.example.boot.entity.IndexReqEntity;
import com.kong.example.boot.util.ElasticUtil;
import com.kong.example.boot.util.RespEntityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/elastic")
@Api(tags = {"elastic使用"})
public class ElasticController {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @PostMapping("/index")
    @ApiOperation("新增修改")
    public RespEntityUtil<IndexResponse> index(@RequestBody IndexReqEntity indexReqEntity) {
        IndexRequest indexRequest = new IndexRequest(indexReqEntity.getIndex(), "doc", indexReqEntity.getId()
        ).source(indexReqEntity.getData());
        IndexResponse index = ElasticUtil.index(indexRequest, RequestOptions.DEFAULT);
        return RespEntityUtil.ok(index);
    }
}
