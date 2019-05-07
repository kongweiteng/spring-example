package com.kong.example.boot.job;

import com.kong.example.boot.rabbit.RabbitMqUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
@EnableScheduling
@Slf4j
public class TestJob {

    @Scheduled(cron = "0/5 * *  * * ? ")   //每*秒执行一次
    @Async
    public void test() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss");
        String nowStr = now.format(format);
        Boolean time = RabbitMqUtil.sendJsonMsgQueue("time", nowStr);
    }

}
