package com.pc.LoginDemo.service;

import com.pc.LoginDemo.entity.LoginTable;
import com.pc.LoginDemo.entity.MiaoshaOrder;
import com.pc.LoginDemo.entity.OrderInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pc.LoginDemo.entity.vo.GoodsVo;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author pc
 * @since 2022-05-28
 */
public interface OrderInfoService extends IService<OrderInfo> {

    /**
     * 根据用户id 和 货物id 查询订单
     *
     * @param uid     用户id
     * @param goodsId 货物id
     * @return 订单信息
     */
    MiaoshaOrder getMiaoShaOrderInfo(Integer uid, long goodsId);

    /**
     * 创建订单
     *
     * @param user  用户信息
     * @param goods 商品信息
     * @return 订单信息
     */
    OrderInfo createOrder(Integer user, GoodsVo goods);

    /**
     * 根据用户id和货品id查询订单信息
     *
     * @param uid     用户uid
     * @param goodsId 商品id
     * @return 订单信息
     */
    OrderInfo getByUId(Integer uid, Long goodsId);

    /**
     * 更新支付状态码
     *
     * @param uid     用户uid
     * @param goodsId 商品id
     */
    void updateStatus(Integer uid, Long goodsId);
}
