package com.pc.ForumDemo.service.impl;

import com.pc.ForumDemo.service.LikeService;
import com.pc.ForumDemo.util.RedisLikeUtil;
import com.pc.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author foreverqisui
 * @date 2022/7/4 13:40
 * @description: 点赞方法的实现类
 */
@Slf4j
@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    @Resource
    RedisTemplate<String, Integer> redisTemplate;

    /**
     * 点赞的方法
     *  @param userId     用户id
     * @param entityType 帖子类型
     * @param entityId   帖子id
     * @return
     */
    @Override
    public Integer like(int userId, int entityType, int entityId) {
        //根据 这俩生成rediskey
        String entityLikely = RedisLikeUtil.getEntityLikely(entityType, entityId);
        // 查询redis这个key是否有userId 这个人
        //做了一个禁止重复点赞的判断
        Boolean member = redisTemplate.opsForSet().isMember(entityLikely, userId);
        if (member == null) {
            log.warn("member is null");
        }
        //如果有 移除掉这个人的赞 --取消点赞的操作
        if (member) {
//            redisTemplate.opsForSet().remove(entityLikely, userId);
            return ResultCode.ERROR;
        } else {
            redisTemplate.opsForSet().add(entityLikely, userId);
            return ResultCode.SUCCESS;
        }
    }

    /**
     * 查询实体点赞的数量
     *
     * @param entityId   帖子id
     * @param entityType 帖子类型
     * @return 点赞数量
     */
    @Override
    public long findEntityLikeCount(int entityId, int entityType) {
        String entityLikely = RedisLikeUtil.getEntityLikely(entityType, entityId);
        return redisTemplate.opsForSet().size(entityLikely);
    }

    /**
     * 查询某人对某实体的点赞状态
     *
     * @param userId     用户id
     * @param entityType 帖子类型
     * @param entityId   帖子id
     * @return 1点赞了 0没点
     */
    @Override
    public int findEntityLikeStatus(int userId, int entityType, int entityId) {
        String entityLikely = RedisLikeUtil.getEntityLikely(entityType, entityId);
        return redisTemplate.opsForSet().isMember(entityLikely, userId) ? 1 : 0;
    }
}
