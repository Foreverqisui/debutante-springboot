package com.pc.ForumDemo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pc.ForumDemo.entity.Message;
import com.pc.ForumDemo.mapper.MessageMapper;
import com.pc.ForumDemo.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pc
 * @since 2022-06-26
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    /**
     * 根据userId查询出列表信息
     *
     * @param userId 通过token查到的信息
     * @return 返回列表信息
     */
    @Override
    public List<Message> getByUserId(String userId) {
        QueryWrapper<Message> messageQueryWrapper = new QueryWrapper<>();
        messageQueryWrapper.eq("from_id",userId).or().eq("to_id",userId);
        List<Message> messages = baseMapper.selectList(messageQueryWrapper);
        return messages;
    }
}
