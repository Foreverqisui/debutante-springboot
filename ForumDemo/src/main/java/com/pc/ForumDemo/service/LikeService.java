package com.pc.ForumDemo.service;


/**
 * @author foreverqisui
 * @date 2022/7/4 13:34
 * @description: 借助redis用来实现点赞
 */
public interface LikeService {

    /**
     * 点赞的方法
     *  @param userId     用户id
     * @param entityType 帖子类型
     * @param entityId   帖子id
     * @return
     */

    Integer like(int userId, int entityType, int entityId);
    /**
     * 查询实体点赞的数量
     * @param entityId 帖子id
     * @param entityType 帖子类型
     * @return 点赞数量
     * */
    long findEntityLikeCount(int entityId, int entityType);

    /**
     * 查询某人对某实体的点赞状态
     * @param userId     用户id
     * @param entityId   帖子id
     * @param entityType 帖子类型
     * @return 1点赞了 0没点
     * */
    int findEntityLikeStatus(int userId, int entityType, int entityId);
}
