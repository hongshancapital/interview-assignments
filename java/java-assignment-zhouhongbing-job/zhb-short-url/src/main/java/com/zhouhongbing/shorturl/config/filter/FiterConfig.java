package com.zhouhongbing.shorturl.config.filter;


import com.zhouhongbing.shorturl.filter.ShortUrlFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @Author heima
 **/
//@Configuration   //开启后运行过滤器
public class FiterConfig {

    @Autowired
    private ShortUrlFilter shortUrlFilter;

    @Bean
    public FilterRegistrationBean registrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(shortUrlFilter); //使用自定义的过滤器

        //设置匹配的路径
        filterRegistrationBean.addUrlPatterns("/*"); //匹配所有的地址
        return filterRegistrationBean;
    }
}
