package org.zxl.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zxl.interceptor.PermissionInterceptor;


@Component
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private PermissionInterceptor permissionHandlerInterceptor;

    /**
     * 注册 拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 权限管理
        registry.addInterceptor(permissionHandlerInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/schema/**", "/sso/**", "/swagger-resources/**", "/webjars/**", "/v2/**",
                        "/swagger-ui.html/**", "/csrf/**", "/error/**");
    }

}