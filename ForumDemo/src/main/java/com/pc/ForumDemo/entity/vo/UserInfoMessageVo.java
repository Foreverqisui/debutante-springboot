package com.pc.ForumDemo.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author testjava
 * @since 2022-04-03
 */
@Data
@TableName("login_table")
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Logintable对象", description="")
public class UserInfoMessageVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer uid;

    private String username;

    private String userId;

    private String pictureoss;
    
    private String content;
}
