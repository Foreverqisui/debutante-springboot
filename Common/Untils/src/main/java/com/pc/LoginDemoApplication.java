package com.pc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author foreverqisui
 */
@SpringBootApplication()
@EnableScheduling
public class LoginDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoginDemoApplication.class,args);
    }
}
