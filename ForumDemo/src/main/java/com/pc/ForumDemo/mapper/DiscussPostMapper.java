package com.pc.ForumDemo.mapper;

import com.pc.ForumDemo.entity.DiscussPost;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pc.ForumDemo.entity.vo.UserInfoDiscussVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pc
 * @since 2022-06-26
 */
@Repository
public interface DiscussPostMapper extends BaseMapper<DiscussPost> {

    /**
     * 联合查询login_table和discuss_post两张表的所需数据
     * @return 评论展示需要的数据
     * */
    @Select("SELECT\n" +
            "\tdiscuss_post.id,\n" +
            "\tusername,\n" +
            "\tlogin_table.user_id,\n" +
            "\tpictureoss,\n" +
            "\ttitle,\n" +
            "\tcontent,\n" +
            "\ttype,\n" +
            "\tcreate_time,\n" +
            "\tcomment_count,\n" +
            "\tscore \n" +
            "FROM\n" +
            "\t`discuss_post`\n" +
            "\tINNER JOIN login_table ON discuss_post.user_id = login_table.user_id \n" +
            "ORDER BY\n" +
            "\ttype desc,create_time ")
    List<UserInfoDiscussVo> findAllInfo();

}
