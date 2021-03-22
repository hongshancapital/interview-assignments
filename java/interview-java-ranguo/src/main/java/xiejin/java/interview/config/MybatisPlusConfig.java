package xiejin.java.interview.config;

import com.baomidou.mybatisplus.core.config.GlobalConfig;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import xiejin.java.interview.handler.UpdateFieldsMetaHandler;

/**
 * @author xiejin
 * @date 2020/7/7 14:18
 */
@Configuration
@Component
@MapperScan("xiejin.java.interview.mapper")
@ConditionalOnClass(value = PaginationInterceptor.class)
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public GlobalConfig globalConfig() {
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setBanner(false);
        globalConfig.setMetaObjectHandler(new UpdateFieldsMetaHandler());
        return globalConfig;
    }

}
