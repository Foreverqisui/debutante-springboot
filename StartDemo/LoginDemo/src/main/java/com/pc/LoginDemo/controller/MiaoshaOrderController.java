package com.pc.LoginDemo.controller;


import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.pc.LoginDemo.entity.LoginTable;
import com.pc.LoginDemo.entity.MiaoshaOrder;
import com.pc.LoginDemo.entity.OrderInfo;
import com.pc.LoginDemo.entity.vo.GoodsVo;
import com.pc.LoginDemo.entity.vo.SecKillMessageVo;
import com.pc.LoginDemo.service.GoodsService;
import com.pc.LoginDemo.service.MiaoshaGoodsService;
import com.pc.LoginDemo.service.OrderInfoService;
import com.pc.LoginDemo.service.rabbitmq.MQSender;
import com.pc.result.ResultBack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现了InitializingBean，可以在初始化时作用
 * 优化后的秒杀
 *
 * @author pc
 * @since 2022-05-28
 */
@RestController
@CrossOrigin
@RequestMapping("/admin/debutante/miaoshaOptimization")
@Slf4j
public class MiaoshaOrderController implements InitializingBean {


    /**
     * 商品服务 用来获取库存
     */
    @Autowired
    private GoodsService goodsService;

    /**
     * 订单服务 用来获取订单状态
     */
    @Autowired
    private OrderInfoService orderService;

    /**
     * 秒杀库存的服务 用来做库存更改的操作
     */
    @Autowired
    private MiaoshaGoodsService miaoShaService;

    /**
     * redis缓存 用来获取订单信息
     */
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * mq发送队列
     */
    @Autowired
    private MQSender mq;

    /**
     * 做内存标记 用来判断是否有库存 -- 减少redis的访问操作
     * 有:true
     * 没有:false
     */
    private Map<Long, Boolean> EmptyStockMap = new HashMap<Long, Boolean>();

    /**
     * 秒杀订单处理
     */
    @RequestMapping(value = "/seckill/{goodsId}/{uid}")
    public ResultBack doSeckill(
            @PathVariable Integer uid,
            @PathVariable long goodsId
    ) {

        //根据goodsId获取 用户信息
        GoodsVo goods = goodsService.getByGoodsId(goodsId);
        //借助valueOperations 做 预减库存
        ValueOperations valueOperations = redisTemplate.opsForValue();
        //自减操作
        Long stock = valueOperations.decrement("secKillGoods:" + goodsId);
        //如果redis获取不到数据 --进行数据库查询
        if (stock == null) {
            //判断是否有库存
            //获取库存
            Integer stockCount = goods.getStockCount();
            //库存不足的情况下
            if (stockCount <= 0) {
                log.warn("stockCount"+stockCount);
                return ResultBack.error().data("questionStatus", 4000);
            }
        }
//        //内存标记
//        if (!EmptyStockMap.get(goodsId)) {
//            log.warn("EmptyStock"+EmptyStockMap.get(goodsId));
//            return ResultBack.error().data("questionStatus", 4000);
//        }

        //判断是否有库存
        if (stock < 0) {
            //证明没有库存了
            EmptyStockMap.put(goodsId, false);
            //还原库存
            valueOperations.increment("secKillGoods", goodsId);
            log.warn("stock"+stock);
            return ResultBack.error().data("questionStatus", 4000);
        }
        //判断缓存中是否有redis键
        Boolean redisExist = redisTemplate.hasKey("order:" + uid + ":" + goodsId);
        if (redisExist) {
            //借助redis缓存 提取 用户订单信息 判断是否秒杀过
            Object secKillOrder = redisTemplate.opsForValue().get("order:" + uid + ":" + goodsId);
            if (secKillOrder != null) {
                return ResultBack.ok().data("questionStatus", 5000);
            }
        } else {
            //备选方案 查询数据库
            MiaoshaOrder order = orderService.getMiaoShaOrderInfo(uid, goodsId);
            if (order != null) {
                return ResultBack.ok().data("questionStatus", 5000);
            }
        }
        //发送mq队列
        SecKillMessageVo messageVo = new SecKillMessageVo(uid, goodsId);
        mq.sendSeckillMessage(JSON.toJSONString(messageVo));
        return ResultBack.ok().data("questionStatus", 3000);
    }


    @GetMapping("/to_result")
    public ResultBack getResult(
            @RequestParam Integer uid, Long goodsId) {
        Long orderId = miaoShaService.getResultBy(uid, goodsId);
        OrderInfo orderInfo = null;
        if (orderId == -1) {
            log.warn("orderIds"+orderId);
            return ResultBack.error().data("questionStatus",4000);
        }else if (orderId ==1){
            return ResultBack.ok().data("questionStatus",3005);
        }
        orderInfo = orderService.getByUId(uid, goodsId);
        return ResultBack.ok().data("orderInfo", orderInfo);
    }

    /**
     * 系统初始化时 加载
     */
    @Override
    public void afterPropertiesSet() throws Exception {

        /*
         * 存放商品进入redis
         * */
        //获取商品信息
        List<GoodsVo> goodsVos = goodsService.listGoodsVo();
        //为空判断
        if (CollectionUtils.isEmpty(goodsVos)) {
            return;
        }
        //遍历放入redis
        goodsVos.forEach(goodsVo ->
        {
            redisTemplate.opsForValue().
                    set("secKillGoods:" + goodsVo.getId(), goodsVo.getStockCount());
            EmptyStockMap.put(goodsVo.getId(), true);
        });
    }
}

