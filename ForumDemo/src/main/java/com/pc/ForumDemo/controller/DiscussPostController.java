package com.pc.ForumDemo.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pc.ForumDemo.common.Jwt;
import com.pc.ForumDemo.entity.Comment;
import com.pc.ForumDemo.entity.DiscussPost;
import com.pc.ForumDemo.entity.Message;
import com.pc.ForumDemo.entity.UserInfo;
import com.pc.ForumDemo.entity.vo.UserInfoDiscussVo;
import com.pc.ForumDemo.entity.vo.UserInfoMessageVo;
import com.pc.ForumDemo.service.*;
import com.pc.result.ResultBack;
import com.pc.result.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author pc
 * @since 2022-06-26
 */
@RestController
@RequestMapping("/admin/ForumDemo/discuss-post")
@CrossOrigin
public class DiscussPostController {

    @Autowired
    DiscussPostService discussPostService;

    @Autowired
    UserInfoService userInfo;

    @Autowired
    CommentService commentService;

    @Autowired
    MessageService messageService;

    @Autowired
    LikeService likeService;

    /**
     * 点赞数量查询
     * */
    @GetMapping("/like/count")
    public ResultBack likeCount(@RequestParam Integer entityType,
                                @RequestParam Integer entityId){
        long entityLikeCount = likeService.findEntityLikeCount(entityId,entityType);
        return ResultBack.ok().data("data",entityLikeCount);
    }

    /**点赞操作*/
    @PostMapping(value = "/like")
    public ResultBack like(@RequestParam Integer entityType,
                           @RequestParam Integer entityId,
                           HttpServletRequest request){
        //获取当前 用户id
        String userId = Jwt.getIdCommentByJwtToken(request);
        Integer code = likeService.like(Integer.parseInt(userId), entityType, entityId);
        if (code.equals(ResultCode.SUCCESS)){
            discussPostService.getByEntityId(entityId);
            return ResultBack.ok().data("code",ResultCode.SUCCESS);
        }else{
            return ResultBack.error();
        }
    }

    /**
     * 根据用户id查询私信列表
     * TODO:逻辑有问题 日后再做
     * */
    @GetMapping("/private/message/list")
    public ResultBack getPrivateMessageList(HttpServletRequest request){
        String userId = Jwt.getIdCommentByJwtToken(request);
        List<Message> list =   messageService.getByUserId(userId);
        System.out.println(list.size());
        List<UserInfoMessageVo> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Integer fromId = list.get(i).getFromId();
            Integer toId = list.get(i).getToId();
            UserInfoMessageVo fromIdS = userInfo.findByUserIdMessage(fromId);
            UserInfoMessageVo toIdS = userInfo.findByUserIdMessage(toId);
            res.add(i,fromIdS);
            res.add(i,toIdS);
        }

        return ResultBack.ok().data("data",res);
    }
    /*
     * TODO：根据targetId找到回复的回复 进行展示 和回复
     * */
    /**
     * 根据entityId回复作者帖子
     * */
    @PostMapping("/replayAuthor")
    public ResultBack replayAuthor(@RequestParam Integer entityId,
                                   @RequestParam String content,
                                   HttpServletRequest request){
        String myselfId = Jwt.getIdCommentByJwtToken(request);
        boolean code = commentService.replayAuthor(entityId,content,myselfId);
        if(code){
            return ResultBack.ok();
        }
        return ResultBack.error();
    }

    /**
     * 根据discuss表的id匹配查询comment的评论数据
     * */
    @GetMapping("/findCommentById")
    public ResultBack findCommentById(@RequestParam String id){
        List<Comment> comments =commentService.findCommentById(id);
        return ResultBack.ok().data("data",comments);
    }

    /**
     * 联合查询表中信息详情页
     */
    @GetMapping("/findAll")
    public ResultBack findAll() {
        List<UserInfoDiscussVo> list = discussPostService.findAllInfo();
        return ResultBack.ok().data("data", list);
    }

    /**
     * 展示帖子详细信息
     */
    @GetMapping("/findByToken/toShow")
    public ResultBack findToShowByTitle(@RequestParam String title,
                                        @RequestParam String userId) {
        DiscussPost data = discussPostService.showInfoByTitle(title,userId);
        return ResultBack.ok().data("data",data);
    }

    /**
     * 发布帖子
     */
    @RequestMapping("/publishPosts")
    public ResultBack publishPosts(@RequestParam String title,
                                   @RequestParam String message,
                                   HttpServletRequest request) {
        //获取user_id
        String userId = Jwt.getIdCommentByJwtToken(request);
        int code = discussPostService.insertTitleMessage(title, message, userId);
        if (code == ResultCode.SUCCESS) {
            return ResultBack.ok().data("flag", ResultCode.SUCCESS);
        } else {
            return ResultBack.ok().data("flag", ResultCode.ERROR);
        }
    }

}

