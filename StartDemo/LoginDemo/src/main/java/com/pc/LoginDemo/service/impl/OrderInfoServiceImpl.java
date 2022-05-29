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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pc
 * @since 2022-05-28
 */
@Service
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoMapper, OrderInfo> implements OrderInfoService {

    @Autowired
    private OrderInfoMapper mapper;

    /**
     * 根据用户id 和 货物id 查询订单
     *
     * @param uid     用户id
     * @param goodsId 货物id
     * @return 订单信息
     */
    @Override
    public MiaoshaOrder getMiaoShaOrderInfo(Integer uid, long goodsId) {
        return mapper.getOrderInfo(uid, goodsId);
    }

    /**
     * 创建订单
     *
     * @param user  用户信息
     * @param goods 商品信息
     * @return 订单信息
     */
    @Override
    public OrderInfo createOrder(Integer user, GoodsVo goods) {
        //创建变量 写入新的订单
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setUserId(Long.valueOf(user));
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setStatus(0);
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setCreateDate(new Date());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        baseMapper.insert(orderInfo);
        //设置秒杀订单的信息
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setUserId(Long.valueOf(user));
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        mapper.insertMiaoShaOrder(miaoshaOrder);
        return orderInfo;
    }

    /**
     * 根据用户id查询订单信息
     *
     * @param uid 用户uid
     * @return 订单信息
     */
    @Override
    public OrderInfo getByUId(Integer uid) {
        //构建条件
        QueryWrapper<OrderInfo> query = new QueryWrapper<>();
        query.eq("user_id", uid);
        OrderInfo orderInfo = baseMapper.selectOne(query);
        //获取数据
        return orderInfo;
    }
}
