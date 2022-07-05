package com.pc.ForumDemo.util;

/**
 * @author foreverqisui
 * @date 2022/7/4 13:42
 * @description: redis点赞的工具类
 */
public class RedisLikeUtil {

    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKELY = "like:entity";

    /**某个实体的赞*/
    public static String getEntityLikely(int entityType,int entityId) {
        return PREFIX_ENTITY_LIKELY + SPLIT + entityType +SPLIT+ entityId;
    }
}
