package com.pc.ForumDemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author pc
 * @since 2022-06-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DiscussPost对象", description="")
public class DiscussPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String userId;

    private String title;

    private String content;

    @ApiModelProperty(value = "0-普通; 1-置顶;")
    private Integer type;

    @ApiModelProperty(value = "0-正常; 1-精华; 2-拉黑;")
    private Integer status;

    private Date createTime;

    private Integer commentCount;

    private Double score;


}
