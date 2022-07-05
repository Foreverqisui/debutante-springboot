package com.pc.ForumDemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pc.ForumDemo.entity.DiscussPost;
import com.pc.ForumDemo.entity.UserInfo;
import com.pc.ForumDemo.entity.vo.UserInfoDiscussVo;
import com.pc.ForumDemo.mapper.DiscussPostMapper;
import com.pc.ForumDemo.service.DiscussPostService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pc.ForumDemo.service.UserInfoService;
import com.pc.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pc
 * @since 2022-06-26
 */
@Service
public class DiscussPostServiceImpl extends ServiceImpl<DiscussPostMapper, DiscussPost> implements DiscussPostService {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    DiscussPostMapper discussPostMapper;

    /**
     * 发布帖子
     *
     * @param title   标题
     * @param message 内容
     * @return 是否成功 200成功 201失败
     */
    @Override
    public int insertTitleMessage(String title, String message, String userId) {
        DiscussPost discussPost = new DiscussPost();
        discussPost.setUserId(userId);
        discussPost.setTitle(title);
        discussPost.setScore(0.0);
        discussPost.setType(0);
        discussPost.setCommentCount(0);
        discussPost.setStatus(0);
        discussPost.setContent(message);
        discussPost.setCreateTime(new Date());
        int flag = baseMapper.insert(discussPost);
        if (flag == 1) {
            return ResultCode.SUCCESS;
        } else {
            return ResultCode.ERROR;
        }

    }

    /**
     * 根据userId获取信息
     *
     * @param userId 评论用户id
     * @return 数据信息
     */
    @Override
    public List<DiscussPost> getByUserId(String userId) {
        QueryWrapper<DiscussPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<DiscussPost> discussPost = baseMapper.selectList(queryWrapper);
        return discussPost;
    }

    /**
     * 查询所有信息
     *
     * @return 联合查询获取表两张表的联合信息
     */
    @Override
    public List<UserInfoDiscussVo> findAllInfo() {
        return discussPostMapper.findAllInfo();
    }

    /**
     * 根据标题和作者名字进行查询
     * @param title 文章标题
     * @param userId 作者id
     * @return 文章信息
     */
    @Override
    public DiscussPost showInfoByTitle(String title, String userId) {
        QueryWrapper<DiscussPost> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",title).eq("user_id",userId);
        DiscussPost discussPost = baseMapper.selectOne(queryWrapper);
        return discussPost;
    }

    /**
     * 根据帖子id 更改数据库中点赞数
     *
     * @param entityId 帖子id
     */
    @Override
    public void getByEntityId(Integer entityId) {
        QueryWrapper<DiscussPost> discussPostQueryWrapper = new QueryWrapper<>();
        discussPostQueryWrapper.eq("id",entityId);
        DiscussPost discussPost = baseMapper.selectOne(discussPostQueryWrapper);
        discussPost.setScore(discussPost.getScore()+1);
        baseMapper.update(discussPost,discussPostQueryWrapper);
    }
}
