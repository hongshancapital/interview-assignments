package com.xiaoxi666.tinyurl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @Author: xiaoxi666
 * @Date: 2022/4/10
 * @Version: 1.0
 * @Description:
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class Launcher {

    public static void main(String[] args) {
        SpringApplication.run(Launcher.class);
    }
}
