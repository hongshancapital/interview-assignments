package com.wigo.config;

import com.wigo.util.NetworkUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;


@Component
@SuppressWarnings(value = {"deprecation"})
@Slf4j
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/*/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowCredentials(false).maxAge(3600);
    }

    /**
     * 添加自定义的Converters和Formatters.
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        ////添加自定义转换器
        //registry.addConverter(new BillboardConfigConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

                String host = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
                response.addHeader("host", host);

                request.setAttribute("requestStartTime", System.currentTimeMillis());
                request.setAttribute("userIp", NetworkUtil.getIpAddress(request));

                StringBuffer parameters = new StringBuffer();
                Enumeration<String> parameterNames = request.getParameterNames();
                while (parameterNames.hasMoreElements()) {
                    String name = parameterNames.nextElement();
                    String value = request.getParameter(name);
                    parameters.append(name).append("->").append(value).append(",");
                }
                String ps = parameters.length() > 0 ? parameters.substring(0, parameters.length() - 1) : "";
                log.info("用户调用接口:{}，参数:[{}]", request.getRequestURI(), ps);
                return true;
            }

            @Override
            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
                long requestStartTime = (long) request.getAttribute("requestStartTime");
                request.removeAttribute("requestStartTime");
                long requestEndTime = System.currentTimeMillis();

                log.info("用户请求[{}]总计用了[{}]ms！", request.getRequestURI(), requestEndTime - requestStartTime);
            }

            /**
             * 解决跨域问题，也可以通过注解@CrossOrigin或是配置nginx解决
             * @author wigo.chen
             * @date 2021/7/27 9:32 下午
             * @param request 包含请求信息
             * @param response 请求返回信息
             * @param handler 处理者
             * @param ex 异常
             * @return void
             **/
            @Override
            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

            }
        }).addPathPatterns("/*/*", "/**");
    }
}
