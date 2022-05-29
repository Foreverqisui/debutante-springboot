package com.pc.LoginDemo.config;

import com.pc.LoginDemo.job.DateTimeJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author foreverqisui
 * quartz框架配置类
 */
@Configuration
public class QuartzConfig {
//    @Bean
//    public JobDetail printTimeJobDetail() {
//        //PrintTimeJob我们的业务类
//        return JobBuilder.newJob(DateTimeJob.class)
//                //可以给该JobDetail起一个id
//                .withIdentity("DateTimeJob")
//                //每个JobDetail内都有一个Map，包含了关联到这个Job的数据，在Job类中可以通过context获取
//                //关联键值对
//                .usingJobData("msg", "Hello Quartz")
//                //即使没有Trigger关联时，也不需要删除该JobDetail
//                .storeDurably()
//                .build();
//    }
//
//    @Bean
//    public Trigger printTimeJobTrigger() {
//        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("1 * * * * ?");
//        return TriggerBuilder.newTrigger()
//                //关联上述的JobDetail
//                .forJob(printTimeJobDetail())
//                //给Trigger起个名字
//                .withIdentity("quartzTaskService")
//                .withSchedule(cronScheduleBuilder)
//                .build();
//    }
}