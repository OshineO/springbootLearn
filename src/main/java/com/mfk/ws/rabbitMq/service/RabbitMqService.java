package com.mfk.ws.rabbitMq.service;

import com.mfk.ws.bean.Permission;
import com.mfk.ws.bean.User;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;


@Service
@RabbitListener(queues = {"hello.java.queue"})
public class RabbitMqService {

    Logger logger = LoggerFactory.getLogger(RabbitMqService.class);

    // 同一队列里的消息只有一个消息处理完成了即接收该消息的方法执行完成，才能收到下一个消息，无论是被谁收到
    @RabbitHandler
    public void receiveMessageUser(Message msg, User user, Channel channel) throws Exception{

        logger.info("方法receiveMessageUser收到消息"+user);
        Thread.sleep(10000);
        logger.info("方法receiveMessageUser处理完成");
        // channel 内自增
        long deliveryTag = msg.getMessageProperties().getDeliveryTag();
        // 手动签收消息
        channel.basicAck(deliveryTag, false);

    }
    @RabbitHandler
    public void receiveMessagePermission(Permission permission) throws Exception{

        logger.info("方法receiveMessagePermission收到消息"+permission);

    }
}
