package com.example.kafka.kafkademo;

import com.example.kafka.kafkademo.entity.MetricEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class KafkaDemoApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(KafkaDemoApplication.class, args);

        KafkaSender sender = context.getBean(KafkaSender.class);

        MetricEvent metric = new MetricEvent();

        metric.setName("mem");
        Map<String, String> tags = new HashMap<>();
        Map<String, Object> fields = new HashMap<>();

        tags.put("cluster", "zhisheng");
        tags.put("host_ip", "101.147.022.106");

        fields.put("used_percent", 90d);
        fields.put("max", 27244873d);
        fields.put("used", 17244873d);
        fields.put("init", 27244873d);

        metric.setTags(tags);
        metric.setFields(fields);


        while (true) {
            long timeMillis = System.currentTimeMillis();
            metric.setTimestamp(timeMillis);
            //调用消息发送类中的消息发送方法
            sender.send("alert-metrics",metric);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
