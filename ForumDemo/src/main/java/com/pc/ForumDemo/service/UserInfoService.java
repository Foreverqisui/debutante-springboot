package com.pc.ForumDemo.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pc.ForumDemo.entity.DiscussPost;
import com.pc.ForumDemo.entity.UserInfo;
import com.pc.ForumDemo.entity.vo.UserInfoMessageVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author testjava
 * @since 2022-04-03
 */
public interface UserInfoService extends IService<UserInfo> {

    /**
     * 通过userId获取用户信息
     * @param userId 评论用户id
     * @return 用户所有信息
     * */
    UserInfo findByUserId(String userId);

    /**
     * 根据查询到的私信来往id 进行回复内容 和个人信息的联合查询
     * @param userId 获取到的来往id
     * @return 联合查询的内容
     * */
    UserInfoMessageVo findByUserIdMessage(Integer userId);
}
