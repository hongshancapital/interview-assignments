package city.yyds.job.hsdemo.config;

import city.yyds.job.hsdemo.filter.ShortUrlFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean reqResFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        ShortUrlFilter shortUrlFilter = new ShortUrlFilter();
        filterRegistrationBean.setFilter(shortUrlFilter);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

    @Bean
    public ShortUrlFilter shortUrlFilter() {
        return new ShortUrlFilter();
    }
}
