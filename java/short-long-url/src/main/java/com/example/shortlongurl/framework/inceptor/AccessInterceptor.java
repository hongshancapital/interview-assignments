package com.example.shortlongurl.framework.inceptor;

import com.example.shortlongurl.framework.exception.CustomException;
import com.example.shortlongurl.framework.utils.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AccessInterceptor implements HandlerInterceptor {
    @Value("${short.url.prefix}")
    private String shortUrlPrefix;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String shortUrl = request.getParameter("shortUrl");
        if(!StringUtils.isEmpty(shortUrl)
                && !StringUtils.startsWith(shortUrl,shortUrlPrefix)){
            throw new CustomException("非法链接格式");
        }
        return true;
    }



}
