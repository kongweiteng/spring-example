package com.example.kafka.kafkademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class KafkaDemoApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(KafkaDemoApplication.class, args);

        KafkaSender sender = context.getBean(KafkaSender.class);

        while (true) {
            //调用消息发送类中的消息发送方法
            sender.send();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
