package com.pc.ForumDemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author foreverqisui
 * redis配置类 -- 指定kv的序列化方式
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        //实例化
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<String, Object>();
        //key序列化 string类型序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //hash类型 key序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //jackson序列化方式
        Jackson2JsonRedisSerializer jackson = new Jackson2JsonRedisSerializer(Object.class);
        //value jackson类型反序列化
        redisTemplate.setValueSerializer(jackson);
        //hash value String类型反序列化
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        //注入连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }
}
