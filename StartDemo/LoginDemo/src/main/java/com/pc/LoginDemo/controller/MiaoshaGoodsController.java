package com.pc.LoginDemo.controller;


import com.pc.LoginDemo.entity.LoginTable;
import com.pc.LoginDemo.entity.MiaoshaOrder;
import com.pc.LoginDemo.entity.OrderInfo;
import com.pc.LoginDemo.entity.vo.GoodsVo;
import com.pc.LoginDemo.service.GoodsService;
import com.pc.LoginDemo.service.MiaoshaGoodsService;
import com.pc.LoginDemo.service.OrderInfoService;
import com.pc.result.ResultBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author pc
 * @since 2022-05-28
 */
@RestController
@RequestMapping("/admin/debutante/miaosha")
@CrossOrigin
public class MiaoshaGoodsController {

    /**
     * 商品服务 用来获取库存
     * */
    @Autowired
    private GoodsService goodsService;

    /**
     * 订单服务 用来获取订单状态
     * */
    @Autowired
    private OrderInfoService orderService;

    /**
     * 秒杀库存的服务 用来做库存更改的操作
     * */
    @Autowired
    private MiaoshaGoodsService miaoShaService;
    /**
     * 秒杀订单处理
     */
    @RequestMapping(value = "/seckill/{goodsId}/{uid}")
    public ResultBack doSeckill(
            @PathVariable Integer uid,
            @PathVariable long goodsId
    ) {
        //判断是否有库存
        //根据goodsId获取 用户信息
        GoodsVo goods = goodsService.getByGoodsId(goodsId);
        //获取库存
        Integer stockCount = goods.getStockCount();
        //库存不足的情况下
        if (stockCount <= 0) {
            return ResultBack.error().data("questionStatus", 500);
        }
        //判断是否秒杀到
        MiaoshaOrder order =  orderService.getMiaoShaOrderInfo(uid, goodsId);
        if(order!=null){
            return ResultBack.ok().data("questionStatus",666);
        }
        //减少库存 下订单 写入秒杀订单
        OrderInfo orderInfo =  miaoShaService.miaoSha(uid,goods);
        return ResultBack.ok().data("orderInfo",orderInfo);
    }
}

