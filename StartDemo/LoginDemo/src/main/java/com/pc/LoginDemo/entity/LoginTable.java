package com.pc.LoginDemo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @author testjava
 * @since 2022-04-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Logintable对象", description="")
public class LoginTable implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer uid;

    private String username;

    private String password;

    private String keycode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date logintime;

    private String collage;

    private String grade;

    private String classes;

    private String inventcode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date uploadtime;

    private String status;

    private String pictureoss;

    private Integer property;

    private String userId;

    private Integer tagStatus;

}
