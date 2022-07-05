package com.pc.ForumDemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pc.ForumDemo.entity.UserInfo;
import com.pc.ForumDemo.entity.vo.UserInfoMessageVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-04-03
 */
@Repository
public interface UserInfoMapper extends BaseMapper<UserInfo> {

    /**
     * 联合 message和login表 获取到 信息
     * @param userId 来往id
     * @return 联合内容
     * */
    @Select("SELECT DISTINCT username,pictureoss,content,from_id,to_id from login_table INNER JOIN message ON login_table.user_id = #{userId}")
    UserInfoMessageVo findByUserIdMessage(Integer userId);
}
