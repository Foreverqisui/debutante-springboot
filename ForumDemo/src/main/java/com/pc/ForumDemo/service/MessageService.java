package com.pc.ForumDemo.service;

import com.pc.ForumDemo.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pc
 * @since 2022-06-26
 */
public interface MessageService extends IService<Message> {

    /**
     * 根据userId查询出列表信息
     * @param userId 通过token查到的信息
     * @return 返回列表信息
     * */
    List<Message> getByUserId(String userId);
}
