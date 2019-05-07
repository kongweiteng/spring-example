package com.kong.example.boot.rest.test;

import com.kong.example.boot.util.RedisUtils;
import com.kong.example.boot.util.RespEntityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
@Api(tags = {"redis使用"})
public class RedisController {

    @PostMapping("/set")
    @ApiOperation("set方法")
    public RespEntityUtil<Boolean> redisSet(@RequestParam("key") String key, @RequestParam("value") String value) {
        Boolean aBoolean = RedisUtils.setString(key, value);
        return RespEntityUtil.ok(aBoolean);
    }

    @GetMapping("/get")
    @ApiOperation("get方法")
    public RespEntityUtil<Object> redisGet(@RequestParam("key") String key) {
        Object string = RedisUtils.getString(key);
        return RespEntityUtil.ok(string);
    }

}
