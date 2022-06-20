package com.pc.LoginDemo.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author foreverqisui
 * 用来给mq作为发送的实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SecKillMessageVo {
    private Integer uid;
    private Long goodsId;
}
