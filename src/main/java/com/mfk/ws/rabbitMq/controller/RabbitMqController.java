package com.mfk.ws.rabbitMq.controller;

import com.mfk.ws.bean.Permission;
import com.mfk.ws.bean.User;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jws.soap.SOAPBinding;

@Controller
@RequestMapping("rabbitMq")
public class RabbitMqController {

    @Autowired
    RabbitTemplate rabbitTemplate;
    @RequestMapping("/sendMessage")
    @ResponseBody
    public void testSendMessage(@RequestParam(required = false ,defaultValue = "10") Integer num) {

        for (int i=0;i<num;i++){
            if (i%2==0) {
                User user = new User();
                user.setId(i);
                user.setUserName("UserName"+i);
                rabbitTemplate.convertAndSend("hello.direct.exchange","hello.java", user,new CorrelationData(""+i));
            } else {
                Permission permission = new Permission();
                permission.setId(i);
                permission.setName("permissionName"+i);
                rabbitTemplate.convertAndSend("hello.direct.exchange","hello.java", permission,new CorrelationData(""+i));
            }
        }

    }

}
