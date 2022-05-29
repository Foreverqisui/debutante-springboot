package com.pc.LoginDemo.service.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * @author foreverqisui
 * rabbitmq 接收方
 */
@Service
@Slf4j
public class MQReceiver {

    /**监听queue队列*/
    @RabbitListener(queues = "queue")

    public void receive(Object msg){
        log.info("接收消息："+msg);
    }
}
