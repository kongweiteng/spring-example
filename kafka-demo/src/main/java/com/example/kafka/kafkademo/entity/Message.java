package com.example.kafka.kafkademo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private Long id;    //id

    private Object msg; //消息

    private Date sendTime;  //时间戳
}
