package com.pc.LoginDemo.service.impl;

import com.pc.LoginDemo.entity.Goods;
import com.pc.LoginDemo.entity.MiaoshaGoods;
import com.pc.LoginDemo.entity.vo.GoodsVo;
import com.pc.LoginDemo.mapper.GoodsMapper;
import com.pc.LoginDemo.service.GoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pc
 * @since 2022-05-28
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {

    @Autowired
    GoodsMapper mapper;

    /**
     * 商品表 和 秒杀商品表 联合信息查询
     * @return 返回的是联合查询的数据
     * */
    @Override
    public List<GoodsVo> listGoodsVo() {
        return mapper.listGoodsVo();
    }

    /**
     * 根据goodsId获取商品信息
     *
     * @param goodsId 货物id  通过学花算法求得
     * @return 详情页信息
     */
    @Override
    public GoodsVo getByGoodsId(long goodsId) {
        return mapper.getByGoodsId(goodsId);
    }

    /**
     * 减少库存
     *
     * @param goods 需要减少的商品
     */
    @Override
    public void reduceStock(GoodsVo goods) {
        //创建商品
        MiaoshaGoods goodInfo = new MiaoshaGoods();
        //设置id
        goodInfo.setGoodsId(goods.getId());
        //减少库存
        mapper.reduceStock(goodInfo);
    }

}
