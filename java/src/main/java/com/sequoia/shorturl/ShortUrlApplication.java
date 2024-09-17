package com.sequoia.shorturl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 短域名长度最大为 8 个字符
 * 采用 SpringBoot，集成 Swagger API文档；
 * JUnit 编写单元测试, 使用 Jacoco 生成测试报告(测试报告提交截图即刻)；
 * 映射数据存储在 JVM 内存即可，防止内存溢出；
 */
@SpringBootApplication
public class ShortUrlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortUrlApplication.class, args);
    }

}
