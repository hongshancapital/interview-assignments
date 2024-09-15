package com.icbc.gjljfl.config;

import com.icbc.gjljfl.util.DateUtils;
import com.icbc.gjljfl.util.LogUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/**
 * 接口拦截器
 * 主要功能: 提供接口访问的一些信息的采集,比如请求的参数 请求的时间 uri等
 */
@Component
public class ApiInterceptor implements HandlerInterceptor {
    private static String serviceName = ApiInterceptor.class.getName();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String position = "preHandle";
        Long startTime = System.currentTimeMillis();
        request.setAttribute("start", startTime);
        LogUtils.info(serviceName, position, "url param", request.getQueryString());
        LogUtils.info(serviceName, position, "uri", request.getRequestURI());
        LogUtils.info(serviceName, position, "IP", request.getRemoteHost());
        LogUtils.info(serviceName, position, "time", String.valueOf(startTime));
        LogUtils.info(serviceName, position, "method", request.getMethod());
        LogUtils.info(serviceName, position, "content_type", request.getContentType());
        return true;
    }

    //渲染之前
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String position = "postHandle";
        HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper(response);
        Long finishTime = System.currentTimeMillis();
        Long startTime = (Long) request.getAttribute("start");
        String takeTime = DateUtils.computeTime(startTime, finishTime);
        LogUtils.info(serviceName, position, "takeTime", takeTime);
        //LogUtils.info(serviceName, position, "响应结果", wrapper.toString());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
