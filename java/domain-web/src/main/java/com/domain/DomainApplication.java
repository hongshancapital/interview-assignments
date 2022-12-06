package com.domain;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author: xielongfei
 * @date: 2022/01/09
 * @description: 启动类
 */
@SpringBootApplication(scanBasePackages = "com.domain",
        exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@Slf4j
@EnableScheduling
@RestController
public class DomainApplication {

    public static void main(String[] args) {
        SpringApplication.run(DomainApplication.class, args);
    }

    @GetMapping(value = "/")
    public ModelAndView api(){
        return new ModelAndView("redirect:/swagger-ui/index.html");
    }
}
