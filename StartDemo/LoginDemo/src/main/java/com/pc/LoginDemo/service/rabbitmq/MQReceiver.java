package com.pc.LoginDemo.service.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.pc.LoginDemo.entity.OrderInfo;
import com.pc.LoginDemo.entity.vo.GoodsVo;
import com.pc.LoginDemo.entity.vo.SecKillMessageVo;
import com.pc.LoginDemo.service.GoodsService;
import com.pc.LoginDemo.service.MiaoshaGoodsService;
import com.pc.result.ResultBack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author foreverqisui
 * rabbitmq 接收方
 */
@Component
@Slf4j
public class MQReceiver {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MiaoshaGoodsService miaoShaService;

    /**
     * 通过Topic监听队列
     * */
    @RabbitListener(queues = "secKillQueue")
    public void receive(String msg) {
        SecKillMessageVo message = JSON.parseObject(msg,SecKillMessageVo.class);
        //获取用户id
        Integer uid = message.getUid();
        //获取商品id
        Long goodsId = message.getGoodsId();
        //通过商品id获取商品信息
        GoodsVo goodsVo = goodsService.getByGoodsId(goodsId);
        //判断库存
        if (goodsVo.getStockCount()<1){
           return;
        }
        //借助redis缓存 提取 用户订单信息 判断是否秒杀过
        Object secKillOrder = redisTemplate.opsForValue().get("order:" + uid + ":" + goodsId);
        if (secKillOrder != null) {
            return;
        }
        //下单操作
        miaoShaService.miaoSha(uid, goodsService.getByGoodsId(goodsId));

    }
}
