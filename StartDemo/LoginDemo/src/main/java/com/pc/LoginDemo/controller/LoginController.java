package com.pc.LoginDemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pc.LoginDemo.aspect.AspectLog;
import com.pc.LoginDemo.entity.LoginTable;
import com.pc.LoginDemo.service.LoginService;
import com.pc.common.Jwt;
import com.pc.result.ResultBack;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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
     * */
    @PostMapping("uploadTime")
    public ResultBack upLoadTime(@RequestBody LoginTable loginTable){
        loginTable.setUploadtime(new Date());
        loginTable.setStatus("1");
        loginService.updateById(loginTable);
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
     * TODO 没有限制扣钱，会一直发送扣钱请求 没有做状态码的管理 前端只能收到剩余钱数
     * */
    @PostMapping("/reduceProperty/{uid}")
    public ResultBack reducePropertyByUid(@PathVariable String uid){
        int res = loginService.reducePropertyByUid(uid);
        return ResultBack.ok().data("remainder",res);
    }
    /**
     * 测试sql注入
     * */
    @GetMapping("/sqlmap")
    public ResultBack sqlmap(@RequestParam String id, @RequestParam String password){
        LoginTable byId = loginService.getById(id);
        return ResultBack.ok().data("sql",byId);
    }
}

