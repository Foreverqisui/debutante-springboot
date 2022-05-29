package com.pc.result;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author foreverqisui
 * ApiModelProperty 是swagger参数注解 可以在swagger页面显示这个汉字注释
 */
@Data
public class ResultBack {


    /**
     * 通过格式定义，返回是否成功的状态码
     * */
    @ApiModelProperty(value = "是否成功")
    private Boolean success;

    /**
     * 通过格式定义，设定返回码
     * */
    @ApiModelProperty(value = "返回码")
    private int code;

    /**
     * 通过格式定义，返回消息值
     * */
    @ApiModelProperty(value = "返回消息")
    private String message;

    /**
     * 通过格式定义，返回数据
     * */
    @ApiModelProperty(value = "返回数据")
    private Map<String, Object> data = new HashMap<String, Object>();


    /**
     * 使构造方法私有
     * */
    private ResultBack(){}

    /**
     * 设置成功的静态方法
     * */
    public static ResultBack ok(){
        ResultBack resultBack = new ResultBack();
        resultBack.setSuccess(true);
        resultBack.setCode(ResultCode.SUCCESS);
        resultBack.setMessage("恭喜你，成功了");
        return resultBack;
    }
    /**
     * 设置失败的静态方法
     * */
    public static ResultBack error(){
        ResultBack resultBack = new ResultBack();
        resultBack.setSuccess(false);
        resultBack.setCode(ResultCode.ERROR);
        resultBack.setMessage("不要灰心，仔细检查，总会对的");
        return resultBack;
    }

    public ResultBack success(Boolean success){
        this.setSuccess(success);
        return this;
    }
    public ResultBack message(String message){
        this.setMessage(message);
        return this;
    }
    public ResultBack code(Integer code){
        this.setCode(code);
        return this;
    }
    public ResultBack data(String key, Object value){
        this.data.put(key,value);
        return this;
    }

    public ResultBack data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
