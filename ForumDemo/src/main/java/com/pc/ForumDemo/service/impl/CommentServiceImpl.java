package com.pc.ForumDemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pc.ForumDemo.entity.Comment;
import com.pc.ForumDemo.entity.DiscussPost;
import com.pc.ForumDemo.mapper.CommentMapper;
import com.pc.ForumDemo.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pc.ForumDemo.service.DiscussPostService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    DiscussPostService discussPostService;

    /**
     * 根据id匹配entryId查询信息
     *
     * @param id discuss表中顺序产生的主键id
     * @return 评论数据
     */
    @Override
    public List<Comment> findCommentById(String id) {
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("entity_id", id);
        List<Comment> comments = baseMapper.selectList(commentQueryWrapper);
        return comments;
    }

    /**
     * 回复作者帖子
     *
     * @param entityId 帖子id
     * @param content  文字内容
     * @param myselfId 本人id
     * @return true成功
     */
    @Override
    public boolean replayAuthor(Integer entityId,  String content, String myselfId) {
        //回复作者帖子
        Comment comment = new Comment();
        comment.setUserId(Integer.valueOf(myselfId));
        comment.setEntityId(entityId);
        comment.setCreateTime(new Date());
        comment.setEntityType(1);
        comment.setStatus(0);
        comment.setTargetId(0);
        comment.setContent(content);
        int count = baseMapper.insert(comment);
        //更改回帖总数
        boolean flag = false;
        if (count == 1) {
            //获取当前回帖数
            QueryWrapper<DiscussPost> wrapper = new QueryWrapper<>();
            wrapper.eq("id", entityId);
            DiscussPost discussPost = discussPostService.getOne(wrapper);
            //对回帖总数加1
            discussPost.setCommentCount(discussPost.getCommentCount() + 1);
            //修改帖内数据
            flag = discussPostService.updateById(discussPost);
        }
        return flag;
    }
}
