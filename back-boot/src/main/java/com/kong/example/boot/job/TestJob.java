package com.kong.example.boot.job;

import com.kong.example.boot.rabbit.RabbitMqUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
@EnableScheduling
@Slf4j
public class TestJob {
    @Autowired
    RabbitMqUtil rabbitMqUtil;

    /**
     * 创交换机
     */
    @PostConstruct
    public void initExchange() {
        RabbitMqUtil.creatFanoutExchange("time-exchange");

    }

    @Scheduled(cron = "0/1 * *  * * ? ")   //每*秒执行一次
    @Async
    public void test() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String nowStr = now.format(format);
        Boolean aBoolean = RabbitMqUtil.sendJsonMsg("time-exchange", "", nowStr);
    }

}
