package com.pc.LoginDemo.service;

import com.pc.LoginDemo.entity.LoginTable;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-03
 */
public interface LoginService extends IService<LoginTable> {

    /**
     * 登录功能的实现
     * @param loginData:登录实体类
     * @return 集合
     * */
    List<LoginTable> login(LoginTable loginData);

    /**
     * 单点登录
     * @param loginTable ：用户信息
     * @return token值
     * */
    String loginByToken(LoginTable loginTable);

    /**
     * 注册功能
     * @param loginTable ：信喜
     * @return 判断是否成功的集合
     * */
    List<Integer> register(LoginTable loginTable);

    /**
     * 定时更改是否提交的状态
     * @param loginTable ：传入要更改的id
     * */
    void updateStatus(LoginTable loginTable);

    /**
     * 秒杀订单 扣除money
     * @param uid 学号
     * @return 是否扣除
     * */
    int reducePropertyByUid(String uid);
}
