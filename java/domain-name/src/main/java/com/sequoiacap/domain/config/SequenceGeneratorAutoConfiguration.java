package com.sequoiacap.domain.config;

import com.sequoiacap.domain.common.SequenceGenerator;
import com.sequoiacap.domain.common.SnowflakeSequenceGenerator;
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
