package com.pc.LoginDemo.service.impl;

import com.pc.LoginDemo.entity.Goods;
import com.pc.LoginDemo.entity.LoginTable;
import com.pc.LoginDemo.entity.MiaoshaGoods;
import com.pc.LoginDemo.entity.OrderInfo;
import com.pc.LoginDemo.entity.vo.GoodsVo;
import com.pc.LoginDemo.mapper.MiaoshaGoodsMapper;
import com.pc.LoginDemo.service.GoodsService;
import com.pc.LoginDemo.service.MiaoshaGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pc.LoginDemo.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MiaoshaGoodsServiceImpl extends ServiceImpl<MiaoshaGoodsMapper, MiaoshaGoods> implements MiaoshaGoodsService {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private OrderInfoService orderInfoService;

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
        //调用 orderInfoService 创建订单
        OrderInfo orderInfo = orderInfoService.createOrder(user, goods);
        return orderInfo;
    }
}
