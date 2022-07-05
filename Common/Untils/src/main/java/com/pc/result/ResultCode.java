package com.pc.result;

import org.springframework.stereotype.Component;

/**
 * @author foreverqisui
 */
@Component
public interface ResultCode {

    /**
     * SUCCESS 成功数据访回状态码
     * */
    Integer SUCCESS = 200;

    /**
     * ERROR 失败数据访回状态码
     * */
    Integer ERROR = 201;
}
