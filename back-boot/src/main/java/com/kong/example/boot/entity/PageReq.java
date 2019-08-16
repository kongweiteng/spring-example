package com.kong.example.boot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("分页请求实体")
public class PageReq {
    @JsonIgnore
    @NotNull(message = "current 不能为空")
    private Long current = 1L;


    @JsonIgnore
    @NotNull(message = "size 不能为空")
    private Long size = 10L;

    /**
     * 排序字段
     */
    @JsonIgnore
    private String orderBy;

    /**
     * 升序降序 默认降序
     */
    @JsonIgnore
    private Boolean orderDesc = true;
    /**
     * 条件查询
     */
    @JsonIgnore
    private String conditonMap;

    /**
     * 模糊查询
     */
    @JsonIgnore
    private String likeMap;

    @JsonIgnore
    private String startTime;

    @JsonIgnore
    private String endTime;

}
