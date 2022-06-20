package com.pc.LoginDemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pc.LoginDemo.aspect.AspectLog;
import com.pc.LoginDemo.entity.LoginTable;
import com.pc.LoginDemo.entity.OrderInfo;
import com.pc.LoginDemo.entity.vo.GoodsVo;
import com.pc.LoginDemo.service.GoodsService;
import com.pc.LoginDemo.service.LoginService;
import com.pc.LoginDemo.service.OrderInfoService;
import com.pc.LoginDemo.service.impl.GoodsServiceImpl;
import com.pc.common.Jwt;
import com.pc.result.ResultBack;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-04-03
 */
@RestController
@RequestMapping("/admin/debutante")
@CrossOrigin
public class LoginController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderInfoService orderInfoService;

    static Logger logger=Logger.getLogger(AspectLog.class);
    /**
     * 登录请求
     */
    @PostMapping("/login")
    public ResultBack login(@RequestBody LoginTable loginTable) {

        List<LoginTable> list = loginService.login(loginTable);
        loginTable.setLogintime(new Date());
        return ResultBack.ok().data("list", list);
    }

    /**
     * 根据id查询信息
     */
    @GetMapping("/findLoginById/{id}")
    public ResultBack findById(@PathVariable String id) {
        LoginTable ids = loginService.getById(id);
        return ResultBack.ok().data("userInfoById", ids);
    }


    /**
     * 使用token进行登陆
     */
    @PostMapping("/loginByToken")
    public ResultBack loginByToken(@RequestBody LoginTable loginTable) {
        //发送token值给前端
        String token = loginService.loginByToken(loginTable);
        return ResultBack.ok().data("token", token);
    }

    /**
     * 获取头信息中的token 返回用户信息
     */
    @GetMapping("getByToken")
    public ResultBack getByToken(HttpServletRequest request) {
        //根据request的对象头获取头信息 返回用户id
        String id = Jwt.getMemberIdByJwtToken(request);
        //根据id获取用户信息
        LoginTable userInfo = loginService.getById(id);
        //获取用户名 和 登录时间
        String username = userInfo.getUsername();
        Date logintime = userInfo.getLogintime();
        //格式化 日期
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(logintime);
        //日志输出
        logger.info(String.format("用户[%s],在[%s],登录了网站",username,date));
        //存放信息进入userInfo -- cookie
        Map<String, Object> map = new HashMap<>();
        map.put("userInfo", userInfo);
        return ResultBack.ok().data(map);
    }

    /**
     * 注册功能
     */
    @PostMapping("register")
    public ResultBack register(@RequestBody LoginTable loginTable) {
        List<Integer> data = loginService.register(loginTable);
        if (data.get(0) == -1) {
            return ResultBack.error().data("list", data);
        } else {
            return ResultBack.ok().data("data", data);
        }
    }

    /**
     * 上传照片提交时间并改变状态码
     * 新功能:每次提交增加50property
     * */
    @PostMapping("uploadTime")
    public ResultBack upLoadTime(@RequestBody LoginTable loginTable){
        loginTable.setUploadtime(new Date());
        loginTable.setStatus("1");
        //每次提交截图后+50 property
        loginTable.setProperty(loginTable.getProperty()+50);
        boolean n = loginService.updateById(loginTable);
        return ResultBack.ok();
    }

    /**
     * 获得排行榜
     */
    @ApiOperation(value = "分页查询功能")
    @PostMapping("upLoadDesc/{current}/{limit}")
    public ResultBack findPageHospitalSet(@PathVariable long current,
                                          @PathVariable long limit
    ) {
        //创建page页，用来传递页面参数
        Page<LoginTable> page = new Page<>(current, limit);
        //构建查询条件
        QueryWrapper<LoginTable> queryWrapper = new QueryWrapper<>();

        queryWrapper.orderByAsc("uploadtime");

        //调用方法实现分页
        IPage<LoginTable> uploadPage = loginService.page(page, queryWrapper);
        //返回结果
        return ResultBack.ok().data("uploadPage",uploadPage);
    }
    /**
     * 秒杀后 扣除money
     * @param uid 学号
     * @param goodsId 商品id
     * @return 用户信息
     * */
    @PostMapping("/reduceProperty/{uid}/{goodsId}")
    public ResultBack reducePropertyByUid(@PathVariable Integer uid,
                                          @PathVariable Long goodsId){
        int remainder = loginService.reducePropertyByUid(uid,goodsId);
        //判断扣钱是否成功
        if(remainder==-1){
            //扣钱失败
            logger.warn("扣钱失败，因为你穷，返回5000");
            return ResultBack.error().data("questionStatus",5000);
        }
        //扣钱成功后 根据goodsId获取商品图片
        GoodsVo goodsInfo = goodsService.getByGoodsId(goodsId);
        String goodsImg = goodsInfo.getGoodsImg();
        //做是否支付过的判断
        OrderInfo orderInfo = orderInfoService.getByUId(uid, goodsId);
        //获取订单状态  0:已支付 1:未支付
        Integer status = orderInfo.getStatus();
        //已支付
        if (status == 0){
            //返回2001状态码 表示已经支付
            return ResultBack.ok().data("questionStatus",2001);
        }
        //更新状态码
        orderInfoService.updateStatus(uid,goodsId);
        //将商品图片放入用户的pictureoss中
        int res = loginService.updatePictureOss(uid, goodsImg);
        if (res==2000){
            Map<String, Integer> map = new HashMap<String, Integer>();
            map.put("remainder",remainder);
            map.put("status",res);
            return ResultBack.ok().data("questionStatus",map);
        }
        return ResultBack.error().data("questionStatus",5000);
    }
}

