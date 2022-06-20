package com.pc.LoginDemo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author foreverqisui
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 构建队列 测试 Topic模式
     * 在前面两种的基础上 将路由表变成可 正则匹配的状态 -- 表示哪种队列可以接收消息
     * # 代表随意字符
     * * 代表不可有字符
     */
    private static final String QUEUE = "secKillQueue";
    private static final String TOPICEXCHANGE = "secKillExchange";

    /**
     * 构建路由键
     */
    private static final String TOPICKEY01 = "queue.#";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE);
    }

    /**
     * 构建交换机
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPICEXCHANGE);
    }

    /**
     * 把队列绑定到交换机
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue())
                .to(topicExchange())
                .with(TOPICKEY01);

    }

}
