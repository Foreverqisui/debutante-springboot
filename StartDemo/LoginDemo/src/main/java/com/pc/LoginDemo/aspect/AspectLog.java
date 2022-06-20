package com.pc.LoginDemo.aspect;


import com.pc.LoginDemo.service.LoginService;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author foreverqisui
 */
@Component
@Aspect
public class AspectLog {

    static Logger logger=Logger.getLogger(AspectLog.class);

    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 定义切点 表示 service所有的方法返回值参数
     * */
    @Pointcut("execution(* com.pc.LoginDemo.service.*.*(..))")
    public void pointcut() {

    }
    @Autowired
    private LoginService loginService;
    /**
     * 切点之前
     * */
    @Before("pointcut()")
    public void before(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //为空判断
        HttpServletRequest request = null;
        if (requestAttributes != null) {
            request = requestAttributes.getRequest();
            //ip地址
            String remoteHost = request.getRemoteHost();
            //登陆时间
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            //访问方法
            String target = joinPoint.getSignature().getDeclaringType() + "." + joinPoint.getSignature().getName();
            logger.info(String.format("用户[%s],在[%s],访问了[%s]",remoteHost,date,target));
        }
    }
    /**
     * 切点之后
     * */
    @After("pointcut()")
    public void after(){

    }
    /**
     * 返回值之后
     * */
    @AfterReturning("pointcut()")
    public void afterReturn(){

    }
    /**
     * 抛异常之后
     */
    @AfterThrowing("pointcut()")
    public void afterThrowing(){

    }
    /**
     * 获取所有请求参数，封装为map对象
     */
    public Map<String, Object> getParameterMap(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Enumeration<String> enumeration = request.getParameterNames();
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        StringBuilder stringBuilder = new StringBuilder();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getParameter(key);
            String keyValue = key + " : " + value + " ; ";
            stringBuilder.append(keyValue);
            parameterMap.put(key, value);
        }
        return parameterMap;
    }

}
