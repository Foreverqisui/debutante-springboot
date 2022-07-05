package com.pc.ForumDemo.controller;

import com.pc.ForumDemo.entity.UserInfo;
import com.pc.ForumDemo.service.UserInfoService;
import com.pc.common.Jwt;
import com.pc.result.ResultBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author foreverqisui
 * @date 2022/7/2 15:45
 * @description: 用来获取用户信息
 */
@RestController
@CrossOrigin
@RequestMapping("/forum_info")
public class UserInfoController {


}
