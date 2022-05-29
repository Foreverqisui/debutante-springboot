package com.pc.LoginDemo.controller;


import com.pc.LoginDemo.entity.vo.GoodsVo;
import com.pc.LoginDemo.service.GoodsService;
import com.pc.LoginDemo.service.MiaoshaGoodsService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 *
 * 实现了InitializingBean，可以在初始化时作用
 * @author pc
 * @since 2022-05-28
 */
@RestController
@RequestMapping("/admin/debutante/miaoshaYouHua")
public class MiaoshaOrderController {

}

