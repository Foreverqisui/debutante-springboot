package com.pc.LoginDemo.controller;


import com.pc.LoginDemo.entity.OrderInfo;
import com.pc.LoginDemo.service.OrderInfoService;
import com.pc.result.ResultBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author pc
 * @since 2022-05-28
 */
@RestController
@RequestMapping("/admin/debutante/order")
@CrossOrigin
public class OrderInfoController {

    @Autowired
    OrderInfoService orderInfo;
    /**
     * 获取订单信息
     * */
    @GetMapping("/getOrderInfo")
    public ResultBack getOrderInfoByUId(
            @RequestParam Integer uid
    ){
        OrderInfo info = orderInfo.getByUId(uid);
        return ResultBack.ok().data("orderInfo",info);
    }
}

