package com.pc.ForumDemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pc.ForumDemo.entity.UserInfo;
import com.pc.ForumDemo.entity.vo.UserInfoMessageVo;
import com.pc.ForumDemo.mapper.UserInfoMapper;
import com.pc.ForumDemo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-03
 */
@Service
public class UserInfoImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {


    @Autowired
    UserInfoMapper mapper;

    /**
     * 通过userId获取用户信息
     *
     * @param userId 评论用户id
     * @return 用户所有信息
     */
    @Override
    public UserInfo findByUserId(String userId) {
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        return baseMapper.selectOne(wrapper);

    }

    /**
     * 根据查询到的私信来往id 进行回复内容 和个人信息的联合查询
     *
     * @param userId 获取到的来往id
     * @return 联合查询的内容
     */
    @Override
    public UserInfoMessageVo findByUserIdMessage(Integer userId) {
        UserInfoMessageVo info =  mapper.findByUserIdMessage(userId);
        return info;
    }
}
