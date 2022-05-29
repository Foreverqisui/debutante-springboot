package com.pc.LoginDemo.mapper;

import com.pc.LoginDemo.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pc.LoginDemo.entity.MiaoshaGoods;
import com.pc.LoginDemo.entity.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author pc
 * @since 2022-05-28
 */
@Repository
public interface GoodsMapper extends BaseMapper<Goods> {

    /**
     * 商品和秒杀商品的联合查询
     * @return 联合查询的数据
     * */
    @Select("select g.*,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price " +
            "from miaosha_goods mg " +
            "left join goods g on mg.goods_id = g.id")
    List<GoodsVo> listGoodsVo();
    /**
     * 通过goodsId查询商品信息
     * @param goodsId 货物id 雪花生成
     * @return 商品详情信息
     * */
    @Select("select g.*,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price " +
            "from miaosha_goods mg left join " +
            "goods g on mg.goods_id = g.id where g.id = #{goodsId}")
    GoodsVo getByGoodsId(@Param("goodsId")long goodsId);

    /**
     * 减少库存
     * @param goodInfo 秒杀的库存信息
     * @return 1
     * */
    @Update("update miaosha_goods set stock_count = stock_count-1 where goods_id = #{goodsId}")
    int reduceStock(MiaoshaGoods goodInfo);
}
