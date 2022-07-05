package com.pc.ForumDemo.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author foreverqisui
 * @date 2022/7/3 12:42
 * @description: 用来联合封装用户信息 和 评论信息
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="联合查询对象", description="")
public class UserInfoDiscussVo {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userId;

    private String title;

    private String content;

    @ApiModelProperty(value = "0-普通; 1-置顶;")
    private Integer type;

    private Date createTime;

    private Integer commentCount;

    private Double score;

    private String username;

    private String pictureoss;
}
