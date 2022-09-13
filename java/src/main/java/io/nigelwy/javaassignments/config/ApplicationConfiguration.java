package io.nigelwy.javaassignments.config;

import io.nigelwy.javaassignments.util.Snowflake;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public Snowflake snowflake() {
        return new Snowflake(1L);
    }
}
