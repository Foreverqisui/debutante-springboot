package com.pc.LoginDemo.job;

import com.pc.LoginDemo.service.LoginService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author foreverqisui
 */
public class DateTimeJob extends QuartzJobBean {


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//
//        //获取JobDetail中关联的数据
//        String msg = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("msg");
//        System.out.println("current time :"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "---" + msg);
    }
}
