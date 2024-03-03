package com.domain.utils.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.domain.api.response.BaseResponse;
import com.domain.utils.NetworkUtils;
import com.domain.utils.CacheUtils;
import com.domain.utils.web.annotation.ApiPermission;
import com.domain.utils.web.annotation.ApiRateLimiter;
import com.domain.utils.web.enums.HttpResponseCodeEnum;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 限流拦截器
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
public class RateLimiterInterceptor implements HandlerInterceptor {

    private CacheUtils cacheUtils; //限流缓存

    public RateLimiterInterceptor(CacheUtils cacheUtils){
        this.cacheUtils = cacheUtils;
    }

    /**
     * 拦截HTTP请求，验证其客户端是否限流
     * @param  request
     * @return accessToen
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)  {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod)handler;
            ApiRateLimiter permission = method.getMethodAnnotation(ApiRateLimiter.class);
            if(permission==null) return true;

            String clientIp = NetworkUtils.getIpAddress(request);
            if(StringUtils.isNoneBlank(clientIp)){
                if(!cacheUtils.checkLimit(clientIp)){
                    //限流了
                    try {
                        BaseResponse baseResponse=BaseResponse.buildFail(HttpResponseCodeEnum.BUSY);
                        response.getWriter().write(JSON.toJSONString(baseResponse));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            }
        }

        return true;
    }
}
