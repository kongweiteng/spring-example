package com.kong.example.boot.rest.test;

import com.kong.example.boot.rabbit.RabbitMqUtil;
import com.kong.example.boot.util.RespEntityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbitmq")
@Api(tags = {"rabbitmq使用"})
public class RabbitMqController {

    @PostMapping("/send")
    @ApiOperation("生产者生产消息到交换机")
    public RespEntityUtil<Boolean> send(@RequestParam("exchange") String exchange, @RequestParam("key") String key, @RequestParam("value") String value) {
        Boolean aBoolean = RabbitMqUtil.sendJsonMsg(exchange, key, value);
        return RespEntityUtil.ok(aBoolean);
    }

    @PostMapping("/sendQueue")
    @ApiOperation("生产者生产消息到队列")
    public RespEntityUtil<Boolean> sendJsonMsgQueue(@RequestParam("queue") String queue, @RequestParam("value") String value) {
        Boolean aBoolean = RabbitMqUtil.sendJsonMsgQueue(queue, value+System.currentTimeMillis());
        return RespEntityUtil.ok(aBoolean);
    }

    @PostMapping("/creatQueue")
    @ApiOperation("创建队列")
    public RespEntityUtil<String> creatQueue(@RequestParam("queue") String queue) {
        String s = RabbitMqUtil.creatQueue(queue);
        return RespEntityUtil.ok(s);
    }


    @PostMapping("/creatTopicExchange")
    @ApiOperation("创建topic类型的交换机")
    public RespEntityUtil<Boolean> creatTopicExchange(@RequestParam("exchange") String exchange) {
        Boolean aBoolean = RabbitMqUtil.creatTopicExchange(exchange);
        return RespEntityUtil.ok(aBoolean);
    }


    @PostMapping("/creatFanoutExchange")
    @ApiOperation("创建Fanout类型的交换机")
    public RespEntityUtil<Boolean> creatFanoutExchange(@RequestParam("exchange") String exchange, @RequestParam("key") String key, @RequestParam("queue") String queue) {
        Boolean aBoolean = RabbitMqUtil.creatFanoutExchange(exchange, key, queue);
        return RespEntityUtil.ok(aBoolean);
    }


}
