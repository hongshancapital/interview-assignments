package com.yofei.shortlink.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@ComponentScan(basePackageClasses = {DataSourceConfiguration.class,
                                     CacheConfiguration.class})
@EnableAspectJAutoProxy
@EnableScheduling
public class DomainConfiguration {
}
