package com.example.kafka.kafkademo;

import com.example.kafka.kafkademo.entity.Message;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class KafkaSender {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private Gson gson = new GsonBuilder().create();

    //发送消息方法
    public void send(String topic,Object value) {
        Message message = new Message();
        message.setId(System.currentTimeMillis());
        message.setMsg(value);
        message.setSendTime(new Date());
        kafkaTemplate.send(topic, gson.toJson(message));
    }

    private static long currentMemSize() {
        return MemoryUsageExtrator.currentFreeMemorySizeInBytes();
    }
}
