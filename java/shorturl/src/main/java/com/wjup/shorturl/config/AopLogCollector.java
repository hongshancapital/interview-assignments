package com.wjup.shorturl.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.LogData;
import com.github.collector.LogCollector;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @description: AOP 拦截参数实现
 * @classname: AopLogCollector
 * @author niuxing@huaxiapawn
 * @date 2020/11/24 13:44
*/
@Component
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass = true)
@Slf4j
public class AopLogCollector implements LogCollector {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void collect(LogData logData) {
        try {
            //将请求日志存储到mysql数据库中
            log.info("日志输出"+objectMapper.writeValueAsString(logData));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
