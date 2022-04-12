package com.eagle.shorturl.intercepter;

import com.eagle.shorturl.exception.BussinessException;
import com.eagle.shorturl.result.ResultCodeEnum;
import com.eagle.shorturl.util.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author eagle
 * @description
 */
@Component
@Slf4j
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String ts = request.getHeader("ts");
        String sign = request.getHeader("sign");
        if (StringUtils.isBlank(ts) || StringUtils.isBlank(sign)) {
            throw new BussinessException(ResultCodeEnum.ERROR_AUTH);
        }
        if (!AuthUtil.validate(ts, sign)) {
            throw new BussinessException(ResultCodeEnum.ERROR_AUTH);
        }
        return true;
    }

}
