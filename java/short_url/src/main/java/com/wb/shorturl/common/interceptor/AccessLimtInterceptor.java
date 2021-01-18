package com.wb.shorturl.common.interceptor;

import com.wb.shorturl.common.access.AccessLimit;
import com.wb.shorturl.common.exception.BaseErrorCode;
import com.wb.shorturl.common.exception.BaseExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;


/**
 * @author bing.wang
 * @date 2021/1/8
 */

@Component
public class AccessLimtInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AccessLimtInterceptor.class);

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${access.seconds}")
    private int seconds;

    @Value("${access.max_count}")
    private int maxCount;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (null == accessLimit) {
                return true;
            }
            boolean needLogin = accessLimit.needLogin();

            if (needLogin) {
                //判断是否登录
                Object user = request.getSession().getAttribute("user");
                if (null == user) {
                    response.sendRedirect("login");
                    return false;
                }
            }
            String ip = request.getRemoteAddr();
            String key = request.getServletPath() + ":" + ip;
            String count = (String) redisTemplate.opsForValue().get(key);

            if (null == count) {
                redisTemplate.opsForValue().set(key, 1, seconds, TimeUnit.SECONDS);
                return true;
            }

            if (Integer.parseInt(count) < maxCount) {
                redisTemplate.opsForValue().increment(key);
                return true;
            }

            if (Integer.parseInt(count) >= maxCount) {
                logger.warn("恶意访问:"+key);
                response.sendRedirect("/error/" + BaseErrorCode.SERVER_IS_BUSY.getCode());
                return false;
            }
        }

        return true;
    }
}