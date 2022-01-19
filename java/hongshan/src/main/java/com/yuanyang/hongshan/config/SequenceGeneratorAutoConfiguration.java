package com.yuanyang.hongshan.config;


import com.yuanyang.hongshan.util.SequenceGenerator;
import com.yuanyang.hongshan.util.SnowflakeSequenceGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yuanyang
 * @description
 */
@Configuration
public class SequenceGeneratorAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SequenceGenerator snowflakeSequenceGenerator(@Value("${snowflake.worker.id}") Long workerId,
                                                        @Value("${snowflake.data.center.id}") Long dataCenterId) {
        SnowflakeSequenceGenerator sequenceGenerator = new SnowflakeSequenceGenerator(workerId, dataCenterId);
        sequenceGenerator.init();
        return sequenceGenerator;
    }
}
