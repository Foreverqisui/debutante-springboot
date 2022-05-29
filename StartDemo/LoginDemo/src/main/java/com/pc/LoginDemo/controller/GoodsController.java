package com.pc.LoginDemo.controller;



import com.pc.LoginDemo.entity.vo.GoodsVo;
import com.pc.LoginDemo.service.GoodsService;
import com.pc.result.ResultBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author pc
 * @since 2022-05-28
 */
@RestController
@RequestMapping("/admin/debutante/goods")
@CrossOrigin
public class GoodsController {

    @Autowired
    GoodsService goodService;

    /**
     * 商品和秒杀商品 的联合查询
     */
    @GetMapping("/findGoodsList")
    public ResultBack findGoodsList() {
        List<GoodsVo> goodsVos = goodService.listGoodsVo();
        return ResultBack.ok().data("goodsVos", goodsVos);
    }

    /**
     * 商品详情页跳转
     */
    @GetMapping("/detailById/{goodsId}")
    public ResultBack detailById(@PathVariable long goodsId) {
        //根据 goodsId 获取 商品详情信息
        GoodsVo goods = goodService.getByGoodsId(goodsId);
        //获取秒杀开始时间
        long startDate = goods.getStartDate().getTime();
        //获取秒杀结束时间
        long endDate = goods.getEndDate().getTime();
        //获取当前时间
        long curDate = System.currentTimeMillis();
        //判断当前时间 是否在秒杀时间内
        //秒杀是否开始
        int miaoshaStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        if (curDate < startDate) {
            //秒杀还未开始
            remainSeconds = (int) (startDate - curDate);
        } else if (curDate > endDate) {
            //秒杀结束
            miaoshaStatus = -1;
            remainSeconds = -1;
        } else {
            //秒杀正在进行
            miaoshaStatus = 1;
            remainSeconds = (int) (endDate - startDate);
        }
        //状态判断
        //还未开始
        if (miaoshaStatus == -1) {
            return ResultBack.error().data("remainSeconds", remainSeconds);
        }
        //秒杀正在进行
        else if (miaoshaStatus == 1) {
            return ResultBack.ok().data("good", goods);
        }
        //秒杀还未开始 并归还倒计时
        else {
            return ResultBack.error().data("remainSeconds",remainSeconds);
        }
    }

}

