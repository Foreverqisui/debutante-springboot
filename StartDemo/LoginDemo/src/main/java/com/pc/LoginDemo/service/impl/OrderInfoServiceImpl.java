package com.pc.LoginDemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pc.LoginDemo.entity.LoginTable;
import com.pc.LoginDemo.entity.MiaoshaOrder;
import com.pc.LoginDemo.entity.OrderInfo;
import com.pc.LoginDemo.entity.vo.GoodsVo;
import com.pc.LoginDemo.mapper.OrderInfoMapper;
import com.pc.LoginDemo.service.OrderInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pc.common.MD5;
import com.pc.result.ResultBack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Autowired
    private OrderInfoMapper mapper;

    @Qualifier("redisTemplate")
    @Autowired
    private RedisTemplate redis;

    @Autowired
    private OrderInfoService service;

    /**
     * 根据用户id 和 货物id 查询订单
     *
     * @param uid     用户id
     * @param goodsId 货物id
     * @return 订单信息
     */
    @Override
    public MiaoshaOrder getMiaoShaOrderInfo(Integer uid, long goodsId)
    {
        return mapper.getOrderInfo(uid, goodsId);
    }

    /**
     * 创建订单
     *
     * @param uid  用户信息
     * @param goods 商品信息
     * @return 订单信息 0已支付 1为未支付
     */
    @Override
    public OrderInfo createOrder(Integer uid, GoodsVo goods) {
        //创建变量 写入新的订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(Long.valueOf(uid));
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setStatus(1);
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setCreateDate(new Date());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        baseMapper.insert(orderInfo);
        //设置秒杀订单的信息
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setUserId(Long.valueOf(uid));
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        mapper.insertMiaoShaOrder(miaoshaOrder);
        //放入缓存中
        redis.opsForValue().set("order:"+uid + ":" + goods.getId(),miaoshaOrder);
        return orderInfo;
    }

    /**
     * 根据用户id查询订单信息
     *
     * @param uid 用户uid
     * @return 订单信息
     */
    @Override
    public OrderInfo getByUId(Integer uid,Long goodsId) {
        //构建条件
        QueryWrapper<OrderInfo> query = new QueryWrapper<>();
        query.eq("user_id", uid).eq("goods_id",goodsId);
        OrderInfo orderInfo = baseMapper.selectOne(query);
        //获取数据
        return orderInfo;
    }

    /**
     * 更新支付状态码
     *
     * @param uid     用户uid
     * @param goodsId 商品id
     */
    @Override
    public void updateStatus(Integer uid, Long goodsId) {
        QueryWrapper<OrderInfo> query = new QueryWrapper<>();
        query.eq("user_id", uid).eq("goods_id",goodsId);
        OrderInfo orderInfo = baseMapper.selectOne(query);
        orderInfo.setStatus(0);
        mapper.update(orderInfo,query);
    }

}
