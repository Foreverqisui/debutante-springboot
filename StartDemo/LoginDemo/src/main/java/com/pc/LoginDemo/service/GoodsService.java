package com.pc.LoginDemo.service;

import com.pc.LoginDemo.entity.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pc.LoginDemo.entity.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pc
 * @since 2022-05-28
 */
public interface GoodsService extends IService<Goods> {

    /**
     * 商品表 和 秒杀商品表 联合信息查询
     * @return 返回的是联合查询的数据
     * */
    List<GoodsVo> listGoodsVo();

    /**
     * 根据goodsId获取商品信息
     * @param goodsId 货物id  通过学花算法求得
     * @return 详情页信息
     * */
    GoodsVo getByGoodsId(long goodsId);

    /**
     * 减少库存
     * @param goods 需要减少的商品
     * */
    void reduceStock(GoodsVo goods);
}
