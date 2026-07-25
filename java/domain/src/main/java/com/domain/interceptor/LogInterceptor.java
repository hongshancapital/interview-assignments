package com.domain.interceptor;

import com.domain.commons.Constants;
import com.domain.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author ：ji
 * @description：日志打印
 */
@Component
public class LogInterceptor implements HandlerInterceptor {
    private final  static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);
    private static final String start = "start";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        String uuid = CommonUtil.generateUUID();
        MDC.put(Constants.REQUEST_ID, uuid);
        logger.info("logger preHandle {} ({}) to logger",request.getServletPath(), getRemoteIP(request));
        request.setAttribute(start,System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView){
//        String uuid = MDC.get(Constants.REQUEST_ID);
//        logger.info("logger postHandle,remove requestId ({}) 耗时：{}ms", uuid ,System.currentTimeMillis() - (long)request.getAttribute(start));
//        MDC.remove(Constants.REQUEST_ID);
        // Controller方法处理完之后，DispatcherServlet进行视图的渲染之前进入次方法，如果抛出异常则不会进入次方法
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        String uuid = MDC.get(Constants.REQUEST_ID);
        logger.info("logger afterCompletion,remove requestId ({}) 耗时：{}ms", uuid ,System.currentTimeMillis() - (long)request.getAttribute(start));
        MDC.remove(Constants.REQUEST_ID);
    }

    /**
     * 获取用户IP地址
     *
     * 阿里云负载均衡可以配置通过X-Forwarded-For获取真实IP地址
     * @param request
     * @return
     */
    private String getRemoteIP(HttpServletRequest request){
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip)){
            return ip;
        }
        ip = request.getHeader("Proxy-Client-IP");
        if (StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip)){
            return ip;
        }
        ip = request.getHeader("WL-Proxy-Client-IP");
        if (StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip)){
            return ip;
        }
        ip = request.getHeader("HTTP_CLIENT_IP");
        if (StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip)){
            return ip;
        }
        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        if (StringUtils.hasText(ip) && !"unknown".equalsIgnoreCase(ip)){
            return ip;
        }
        ip = request.getRemoteAddr();
        return ip;
    }
}
