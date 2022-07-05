package com.pc.ForumDemo.service;

import com.pc.ForumDemo.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pc
 * @since 2022-06-26
 */
public interface CommentService extends IService<Comment> {

    /**
     * 根据id匹配entryId查询信息
     * @param id discuss表中顺序产生的主键id
     * @return 评论数据
     * */
    List<Comment> findCommentById(String id);

    /**
     * 回复作者帖子
     * @param entityId 帖子id
     * @param content 文章内容
     * @param myselfId 本人id
     * @return boolean成功 其他失败
     * */
    boolean replayAuthor(Integer entityId, String content, String myselfId);
}
