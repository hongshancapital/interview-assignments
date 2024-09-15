package com.icbc.gjljfl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
@ServletComponentScan("com.icbc.gjljfl.config.*")
@ComponentScan("com.icbc.gjljfl.*")
@MapperScan("com.icbc.gjljfl.mapper")
public class GjljflApplication{

    public static void main(String[] args) {
        SpringApplication.run(GjljflApplication.class, args);
    }




}
