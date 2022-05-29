package com.pc.quartz;

import org.junit.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@PersistJobDataAfterExecution
public class Demo {

    @Test
    public void testDateFormat() throws SchedulerException {
        //创建jobDetail实例
        JobDetail jobDetail = JobBuilder.newJob(QuartzTest.class)
                //链式编程设置属性 name：唯一 group：分组
                .withIdentity("job1", "group1")
                .build();
        //触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger", "goup1")
                //立马启动
                .startNow()
                //触发策略 多长时间触发
                .withSchedule(simpleSchedule()
                        //触发时间
                        .withIntervalInSeconds(1)
                        //重复执行
                        .repeatForever())
                .build();

        //调度器
        Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler();

        defaultScheduler.scheduleJob(jobDetail, trigger);
        //启动
        defaultScheduler.start();
    }
}
