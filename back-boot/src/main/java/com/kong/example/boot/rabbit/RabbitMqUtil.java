package com.kong.example.boot.rabbit;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
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
     * 创交换机
     */
    @PostConstruct
    public void initExchange() {
        RabbitMqUtil.creatFanoutExchange("energy-pm-page-refresh");
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
        System.err.println(Thread.currentThread().getName() + "sendJsonMsg");
        try {
            rabbitTemplate.convertAndSend(exchange, key, JSON.toJSONString(object));
        } catch (AmqpException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Async
    public void asyncSendJsonMsg(String exchange, String key, Object object) {
        try {
//            System.err.println(System.currentTimeMillis());
//            Object o = rabbitTemplate.convertSendAndReceive(exchange, key, JSON.toJSONString(object));
            rabbitTemplate.convertAndSend(exchange, key, JSON.toJSONString(object));
//            System.err.println(System.currentTimeMillis());
        } catch (AmqpException e) {
            log.info("异步发送事件失败");
        }

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
        return amqpAdmin.declareQueue(new Queue(queue, true));

    }


    /**
     * 创建 topic 类型的 交换机
     *
     * @param exchange
     * @return
     */
    public static Boolean creatTopicExchange(String exchange) {
        TopicExchange topicExchange = new TopicExchange(exchange, true, false);
        amqpAdmin.declareExchange(topicExchange);
        return true;

    }


    /**
     * 创建 topic 类型的 交换机
     *
     * @param exchange
     * @return
     */
    public static Boolean creatDirectExchange(String exchange) {
        DirectExchange directExchange = new DirectExchange(exchange, true, false);
        amqpAdmin.declareExchange(directExchange);
        return true;

    }

    /**
     * 创建 Fanout 类型的 交换机
     *
     * @param exchange
     * @return
     */
    public static Boolean creatFanoutExchange(String exchange) {
        //创建交换机
        FanoutExchange topicExchange = new FanoutExchange(exchange, true, false);
        amqpAdmin.declareExchange(topicExchange);
        return true;
    }

    public static Boolean bind(String exchange, String queue, String key) {
        Binding binding = new Binding(queue, Binding.DestinationType.QUEUE,
                exchange, key, new HashMap());
        amqpAdmin.declareBinding(binding);
        return true;
    }


    /**
     * 创建 Fanout 类型的 交换机 并且绑定 队列
     *
     * @param exchange
     * @return
     */
    public static Boolean creatFanoutExchange(String exchange, String key, String queue) {
        //创建交换机
        Boolean aBoolean = creatFanoutExchange(exchange);
        //创建队列
        String s = creatQueue(queue);
        //bind
        Boolean bind = bind(exchange, queue, key);
        return true;
    }


    /**
     * 广播刷新页面的消息
     */
    @Async
    public void refresh() {
        Map map = new HashMap();
        map.put("command", "refresh");
        RabbitMqUtil.sendJsonMsg("energy-pm-page-refresh", "", JSON.toJSONString(map));
    }

}
