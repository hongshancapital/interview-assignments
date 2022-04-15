package com.scdt.assignment;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.xiesx.fastboot.db.jpa.annotation.EnableJpaPlusRepositories;

import lombok.extern.log4j.Log4j2;

/**
 * @title AppApplication.java
 * @description
 * @author
 * @date 2022-04-15 17:10:59
 */
@Log4j2
// 启用审计
@EnableJpaAuditing
// 启用JPA
@EnableJpaPlusRepositories
// 启用事物
@EnableTransactionManagement
@SpringBootApplication
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AppApplication.class);
        app.setBannerMode(Banner.Mode.CONSOLE);
        app.run(args);
        log.info("Started Application success");
    }
}
