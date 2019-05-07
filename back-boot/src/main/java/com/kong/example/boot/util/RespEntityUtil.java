package com.kong.example.boot.util;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.io.Serializable;

/**
 * 接口返回实体
 */
@ApiModel("com.enn.vo.energy.EtspResp")
@Data
public class RespEntityUtil<T> implements Serializable {
    @ApiModelProperty(value = "接口返回信息", name = "msg", example = "请求成功！！！")
    private String msg;
    @ApiModelProperty(value = "接口返回码", name = "code", example = "200")
    private Integer code;

    private T data;
    @ApiModelProperty(value = "接口返回错误信息", name = "error", example = "参数错误！！！")
    private String error;

    public static <T> RespEntityUtil<T> ok(T obj) {
        RespEntityUtil respEntity = new RespEntityUtil();
        respEntity.setCode(StatusCodeUtil.SUCCESS.getCode());
        respEntity.setMsg(StatusCodeUtil.SUCCESS.getMsg());
        respEntity.setData(obj);
        return respEntity;
    }
}
