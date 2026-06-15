package com.hongshan.work.config;

import com.hongshan.work.util.SequenceGenerator;
import com.hongshan.work.util.SnowflakeSequenceGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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