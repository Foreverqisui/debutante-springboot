package com.pc.ForumDemo.service;

import com.pc.ForumDemo.entity.DiscussPost;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pc.ForumDemo.entity.vo.UserInfoDiscussVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pc
 * @since 2022-06-26
 */
public interface DiscussPostService extends IService<DiscussPost> {

    /**
     * 发布帖子
     * @param title 标题
     * @param message 内容
     * @param userId 用来查询帖子的id
     * @return 是否成功 200成功 201失败
     * */
    int insertTitleMessage(String title, String message,String userId);

    /**
     * 根据userId获取信息
     * @param userId 评论用户id
     * @return 数据集合
     * */
    List<DiscussPost> getByUserId(String userId);

    /**
     * 查询所有信息
     * @return 联合查询获取表两张表的联合信息
     */
    List<UserInfoDiscussVo> findAllInfo();

    /**
     * 根据标题和作者名字进行查询
     * @param title 文章标题
     * @param userId 作者id
     * @return 文章信息
*/
    DiscussPost showInfoByTitle(String title, String userId);

    /**
     * 根据帖子id 更改数据库中点赞数
     * @param entityId 帖子id
     * */
    void getByEntityId(Integer entityId);
}
