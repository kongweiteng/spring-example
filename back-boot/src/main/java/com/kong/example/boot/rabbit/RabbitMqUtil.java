package com.kong.example.boot.rabbit;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqUtil {
    private static RabbitMessagingTemplate rabbitMessagingTemplate;
    private static RabbitTemplate rabbitTemplate;
    private static AmqpTemplate amqpTemplate;
    private static AmqpAdmin amqpAdmin;

    @Autowired
    public void setRabbitMessagingTemplate(RabbitMessagingTemplate rabbitMessagingTemplate) {
        RabbitMqUtil.rabbitMessagingTemplate = rabbitMessagingTemplate;
    }

    @Autowired
    public void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        RabbitMqUtil.rabbitTemplate = rabbitTemplate;
    }

    @Autowired
    public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
        RabbitMqUtil.amqpTemplate = amqpTemplate;
    }

    @Autowired
    public void setAmqpAdmin(AmqpAdmin amqpAdmin) {
        RabbitMqUtil.amqpAdmin = amqpAdmin;
    }

    /**
     * 发送消息到交换机，并且定义绑定key
     *
     * @param exchange
     * @param key
     * @param object
     * @return
     */
    public static Boolean sendJsonMsg(String exchange, String key, Object object) {
        try {
            rabbitTemplate.convertSendAndReceive(exchange, key, JSON.toJSONString(object));
        } catch (AmqpException e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * 生产消息到队列中
     *
     * @param queue
     * @param object
     * @return
     */
    public static Boolean sendJsonMsgQueue(String queue, Object object) {
        try {
            amqpTemplate.convertAndSend(queue, object);
        } catch (AmqpException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 创建队列
     *
     * @param queue
     * @return
     */
    public static String creatQueue(String queue) {
        return amqpAdmin.declareQueue(new Queue(queue));

    }


    /**
     * 创建 topic 类型的 交换机
     *
     * @param exchange
     * @return
     */
    public static Boolean creatTopicExchange(String exchange) {
        TopicExchange topicExchange = new TopicExchange(exchange);
        amqpAdmin.declareExchange(topicExchange);
        return true;

    }
}
