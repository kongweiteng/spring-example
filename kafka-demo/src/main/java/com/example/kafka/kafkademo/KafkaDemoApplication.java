package com.example.kafka.kafkademo;

import com.google.gson.JsonObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Random;

@SpringBootApplication
public class KafkaDemoApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(KafkaDemoApplication.class, args);

        KafkaSender sender = context.getBean(KafkaSender.class);

        JsonObject object= new JsonObject();
        object.addProperty("equpiId","95432654365");
        object.addProperty("equipMk","METE");


        while (true) {
            object.addProperty("value", new Random().nextInt());
            object.addProperty("time",System.currentTimeMillis());
            //调用消息发送类中的消息发送方法
            sender.send("iot",object);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
