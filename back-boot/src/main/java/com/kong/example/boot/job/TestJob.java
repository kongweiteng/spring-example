package com.kong.example.boot.job;

import com.alibaba.fastjson.JSON;
import com.kong.example.boot.rabbit.RabbitMqUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@EnableScheduling
@Slf4j
public class TestJob {

    @Scheduled(cron = "0/1 * *  * * ? ")   //每1秒执行一次
    @Async
    public void test() {
        Boolean time = RabbitMqUtil.sendJsonMsgQueue("time", JSON.toJSONString(System.currentTimeMillis()));
    }

}
