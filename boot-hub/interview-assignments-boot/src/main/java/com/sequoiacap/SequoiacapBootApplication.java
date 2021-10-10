package com.sequoiacap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *@ClassName: SequoiacapBootApplication
 *@Description: 主启动类
 *@Author: xulong.wang
 *@Date 10/10/2021
 *@Version 1.0
 *
 */
@SpringBootApplication
public class SequoiacapBootApplication {
    /**
     * 启动main方法
     * @param args
     * @return
     */
    public static void main(String[] args) {
        SpringApplication.run(SequoiacapBootApplication.class, args);
    }

}
