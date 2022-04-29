package com.youming.sequoia.sdn.apipublic.config;


import com.youming.sequoia.sdn.apipublic.constant.SpringContextConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableAutoConfiguration // spring boot将根据你添加的依赖自动进行配置
@ComponentScan(basePackages = {"com.youming.sequoia.sdn.apipublic",
        "com.youming.sequoia.sdn.database"}, excludeFilters = {
        @Filter(type = FilterType.ANNOTATION, value = Configuration.class)}) // 自动扫描包范围,相当于xml配置:context:component-scan
@Import({AsyncConfig.class, CorsConfig.class, CaffeineConfig.class, Swagger2Config.class})
public class SpringApplicationConfig {

    @Autowired
    private Environment environment;

    // 专门用于注入保留ApplicationContext的工具类
    @Bean(name = "springContextConstant")
    public SpringContextConstant springUtils() {
        SpringContextConstant springContextConstant = new SpringContextConstant();
        return springContextConstant;
    }

}
