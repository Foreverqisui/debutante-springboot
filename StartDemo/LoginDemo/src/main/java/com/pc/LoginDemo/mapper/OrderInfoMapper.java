package com.pc.LoginDemo.mapper;

import com.pc.LoginDemo.entity.MiaoshaOrder;
import com.pc.LoginDemo.entity.OrderInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pc
 * @since 2022-05-28
 */
@Repository
public interface OrderInfoMapper extends BaseMapper<OrderInfo> {

    /**
     * 查询是否存在秒杀订单
     * @param uid 用户id
     * @param goodsId  货物id
     * @return 秒杀订单信息
     * */
    @Select("select * from miaosha_order where user_id=#{uid} and goods_id=#{goodsId} ")
    MiaoshaOrder getOrderInfo(Integer uid, long goodsId);

    /**
     * 插入属于到 秒杀订单
     * */
    @Insert("insert into miaosha_order(user_id,order_id,goods_id) " +
            "values (#{userId},#{orderId},#{goodsId})")
    void insertMiaoShaOrder(MiaoshaOrder miaoshaOrder);

    /*
    * 查询orderinfo订单信息
    * */
    @Select("SELECT * FROM order_info WHERE user_id = #{uid} AND goods_id = #{goodsId}")
    OrderInfo getOrderInfoByUid(Integer uid, Long goodsId);
}
