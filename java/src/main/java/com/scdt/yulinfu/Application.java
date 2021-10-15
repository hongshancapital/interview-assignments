package com.scdt.yulinfu;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yulinfu
 * @description
 * @data 2021/10/15
 */
@SpringBootApplication(scanBasePackages = {"com.scdt.yulinfu"})
@MapperScan(basePackages = "com.scdt.yulinfu.dao", annotationClass = Mapper.class)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
