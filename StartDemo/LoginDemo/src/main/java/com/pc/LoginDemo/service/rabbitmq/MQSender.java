package com.pc.LoginDemo.service.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author foreverqisui
 * rabbitmq 发送方
 */
@Service
@Slf4j
public class MQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 借助mq进行秒杀信息的发送
     * */
    public void sendSeckillMessage(String msg){
        rabbitTemplate.convertAndSend("secKillExchange",
                "queue.message",msg);
    }
}
