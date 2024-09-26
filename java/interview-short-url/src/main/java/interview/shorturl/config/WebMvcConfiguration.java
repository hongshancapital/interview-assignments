package interview.shorturl.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import interview.shorturl.interception.CommonInterception;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author: ZOUFANQI
 **/
@Configuration
public class WebMvcConfiguration extends WebMvcConfigurationSupport {
    private final CommonInterception commonInterception;

    public WebMvcConfiguration(CommonInterception commonInterception) {
        this.commonInterception = commonInterception;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.commonInterception);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new StringHttpMessageConverter(StandardCharsets.UTF_8));
        converters.add(new MappingJackson2HttpMessageConverter(new ObjectMapper()));
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");

        registry.addResourceHandler("docs.html")
                .addResourceLocations("classpath:/META-INF/resources/");
    }
}
