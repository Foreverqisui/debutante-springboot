package com.pc.LoginDemo.service;

import com.pc.LoginDemo.entity.LoginTable;
import com.pc.LoginDemo.entity.MiaoshaGoods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pc.LoginDemo.entity.OrderInfo;
import com.pc.LoginDemo.entity.vo.GoodsVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pc
 * @since 2022-05-28
 */
public interface MiaoshaGoodsService extends IService<MiaoshaGoods> {

    /**
     * 减少库存 下订单 的操作
     * @param user 用户信息
     * @param goods 商品信息
     * @return 订单信息
     * */
    OrderInfo miaoSha(Integer user, GoodsVo goods);
}
