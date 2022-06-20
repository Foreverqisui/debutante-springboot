package com.pc.LoginDemo.entity.vo;

import com.pc.LoginDemo.entity.Goods;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;
import java.util.Date;

/**
 * @author foreverqisui
 * 用来做秒杀商品 和 商品的共同类
 * */
@Data
public class GoodsVo extends Goods {

    /**
     * 库存数量
     */
    private Integer stockCount;
    /**
     * 秒杀价格
     */
    private Integer miaoshaPrice;
    /**
     * 开始秒杀的时间
     */
    private Date startDate;

    /**
     * 结束秒杀的时间
     */
    private Date endDate;


}