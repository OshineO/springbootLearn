package com.mfk.ws.config;


import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class MyRabbitMqConfig {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Bean
    public MessageConverter messageConverter () {
        return new Jackson2JsonMessageConverter();
    }

   @PostConstruct
    public void initRabbitTemplate() {

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
        /* *
         * @param correlationData 当前消息的唯一关联数据
         * @param ack 消息是成功收到 只要消息抵达 ack就是true
         * @param cause 原因
         * */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("CorrelationData:"+correlationData.getId()
                +"ack："+ack +"cause:"+cause);
            }
        });

        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {

        /*
         * 只要消息没有投递给指定的队列就触发这个回调
         * @param message 投递失败的详细信息
         * @param replyCode 回复的状态码
         * @param replyText 回复的文本内容
         * @param exchange 消息发给的交换机
         * @param routingKey 消息用的哪个路由键
         *
         * */
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                System.out.println(
                       "[message:"+message +"[replyCode:"+replyCode+"[replyText:"+replyText
                        +"[exchange:"+exchange+"[routingKey:"+routingKey
                );
            }
        });

    }



}
