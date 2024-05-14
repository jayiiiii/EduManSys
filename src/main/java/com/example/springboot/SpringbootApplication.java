package com.example.springboot;

import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
public class SpringbootApplication {
    public static void main(String[] args) {
        LogFactory.useCustomLogging(StdOutImpl.class);

        SpringApplication.run(SpringbootApplication.class, args);
    }

}
