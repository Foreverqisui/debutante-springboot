package com.pc.LoginDemo.job;

import com.pc.LoginDemo.controller.LoginController;
import com.pc.LoginDemo.entity.LoginTable;
import com.pc.LoginDemo.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author foreverqisui
 */
@Component
public class JobTask {
    @Autowired
    LoginService loginService;

    //在每周五十点十分进行更新操作

    @Scheduled(cron = "0 51 14 ? * FRI")
    public void jobTask(){
        LoginTable loginTable = new LoginTable();
        for (int i = 0; i < 10; i++) {
            loginTable.setId(i);
            loginService.updateStatus(loginTable);
        }
    }
}
