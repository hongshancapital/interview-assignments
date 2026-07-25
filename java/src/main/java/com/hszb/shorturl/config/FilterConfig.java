package com.hszb.shorturl.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/20 7:31 下午
 * @Version: 1.0
 * @Description: Filter配置
 */

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean initCharacterEncodingFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setForceEncoding(true);
        filter.setEncoding("UTF-8");
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
