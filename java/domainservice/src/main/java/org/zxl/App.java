package org.zxl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.zxl.conf.InitializerRunner;

/**
 * 启动类
 */
@Slf4j
@SpringBootApplication(scanBasePackages = {"org.zxl"}, exclude = {DataSourceAutoConfiguration.class, RedisAutoConfiguration.class})
@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.REGEX, pattern = "org.zxl.proxy.exclude.*")})
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public InitializerRunner initApp() {
        return new InitializerRunner();
    }

}