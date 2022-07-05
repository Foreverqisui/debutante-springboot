package com.pc.LoginDemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author foreverqisui
 */
@SpringBootApplication()
@MapperScan("com.pc.LoginDemo.mapper")
@EnableScheduling
public class LoginDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoginDemoApplication.class,args);
    }
}
