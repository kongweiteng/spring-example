package com.example.datagecco.rest;


import com.alibaba.fastjson.JSONObject;
import com.example.datagecco.task.DemoTask;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dome")
@Api(tags = {"domo"})
public class DemoController {
    @Autowired
    DemoTask demoTask;

    @ApiOperation("测试")
    @GetMapping("/test")
    public JSONObject test() {
        demoTask.test();
        return null;
    }

}
