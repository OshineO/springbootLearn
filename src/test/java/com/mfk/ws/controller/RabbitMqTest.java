package com.mfk.ws.controller;

import com.mfk.ws.bean.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;

/**
 * RabbitAutoConfiguration
 * RabbitTemplate
 * AmqpAdmin
 * RabbitMessagingTemplate
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitMqTest {
    @Autowired
    AmqpAdmin amqpAdmin;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    RabbitMessagingTemplate rabbitMessagingTemplate;
    Logger logger = LoggerFactory.getLogger(RabbitMqTest.class);



    /**
     * 1,创建exchange\queue\binding
     *    1）使用AmqpAdmin
     * 2,收发消息
     *    1）发送消息 rabbitTemplate
     *
     */
    /**
     * 创建交换机
     */
    @Test
    public void createExchange() {
        //String name, boolean durable, boolean autoDelete, Map<String, Object> arguments
        DirectExchange directExchange = new DirectExchange("hello.direct.exchange",true,false);
        amqpAdmin.declareExchange(directExchange);
        logger.info("directExchange【{}】 创建成功","hello.direct.exchange");

    }

    /**
     * 创建队列
     */
    @Test
    public void createQueue() {
        // String name, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        Queue queue = new Queue("hello.java.queue",true, false, false, null);
        amqpAdmin.declareQueue(queue);
        logger.info("queue【{}】 创建成功","hello.java.queue");


    }
    // 绑定
    @Test
    public void createBinding() {
        // String destination, 目的地 要绑定的队列 或交换机
        // DestinationType destinationType, 目的地类型
        // String exchange, 交换机
        // String routingKey,路由键
        // Map<String, Object> arguments 参数
        Binding binding = new Binding("hello.java.queue", Binding.DestinationType.QUEUE, "hello.direct.exchange", "hello.java", null);
        amqpAdmin.declareBinding(binding);
        logger.info("binding【{}】 创建成功");
    }

    // 发消息
    @Test
    public void sendRabbitMessage() {
        String msg = "hello world !";
        // String exchange, String routingKey, Object message
        // 发送String类型
        rabbitTemplate.convertAndSend("hello.direct.exchange","hello.java", msg);
        // 如要发送对象要指定序列化器，如不指定会使用默认的JDK序列化
        // 在rabbitAutoConfiguration创建rabbitTemplate时有序列化器
        User user = new User();
        user.setId(1);
        user.setUserName("sendRabbitMessage");
        rabbitTemplate.convertAndSend("hello.direct.exchange","hello.java", user);
    }



}