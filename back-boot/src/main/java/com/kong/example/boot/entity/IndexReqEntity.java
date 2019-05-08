package com.kong.example.boot.entity;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("index 方法请求参数")
public class IndexReqEntity {
    @ApiModelProperty(value = "索引")
    private String index;
    private String id;
    private JSONObject data;
}
