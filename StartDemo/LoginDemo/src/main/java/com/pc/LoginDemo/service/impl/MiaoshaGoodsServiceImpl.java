package com.pc.LoginDemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pc.LoginDemo.entity.*;
import com.pc.LoginDemo.entity.vo.GoodsVo;
import com.pc.LoginDemo.mapper.MiaoshaGoodsMapper;
import com.pc.LoginDemo.mapper.MiaoshaOrderMapper;
import com.pc.LoginDemo.service.GoodsService;
import com.pc.LoginDemo.service.MiaoshaGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pc.LoginDemo.service.OrderInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pc
 * @since 2022-05-28
 */
@Service
@Slf4j
public class MiaoshaGoodsServiceImpl extends ServiceImpl<MiaoshaGoodsMapper, MiaoshaGoods> implements MiaoshaGoodsService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private MiaoshaOrderMapper mapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 判断redis里是否有订单
     * */
    private static final String EMPTYKEY = "isStockEmpty:";
    /**
     * 减少库存 下订单 的操作 需要进行事务操作
     *
     * @param user  用户信息
     * @param goods 商品信息
     * @return 订单信息
     */
    @Override
    @Transactional
    public OrderInfo miaoSha(Integer user, GoodsVo goods) {
        //调用 goodsService 减少库存
        goodsService.reduceStock(goods);
        //判断是否还有内存
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        if (goods.getStockCount()<1){
            valueOperations.set(EMPTYKEY+goods.getId(),"-1");
            return null;
        }
        //调用 orderInfoService 创建订单
        OrderInfo orderInfo = orderInfoService.createOrder(user, goods);
        return orderInfo;
    }

    /**
     * 在之前已经发送mq队列 去 下订单
     * 通过uid和goodsId获取秒杀结果
     * 为了后续判断是否秒杀成功
     *
     * @param uid     用户id
     * @param goodsId 商品id
     * @return 状态号 1：排队中 -1为失败
     */
    @Override
    public Long getResultBy(Integer uid, Long goodsId) {
       //根据 uid 和goodsId 构建查询
        MiaoshaOrder miaoshaOrder = mapper.selectOne(new QueryWrapper<MiaoshaOrder>()
                .eq("user_id", uid).eq("goods_id", goodsId));
        //判断 订单是否为空
        if (miaoshaOrder != null) {
            //证明已经抢购过 -- 秒杀成功
            return miaoshaOrder.getOrderId();
        }else if(redisTemplate.hasKey(EMPTYKEY+goodsId)){
            //秒杀失败
            return -1L;
        }else {
            //排队中
            return 1L;
        }
    }
}
