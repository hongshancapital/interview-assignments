package com.interceptor;

import com.alibaba.fastjson.JSON;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RequestInterceptor  extends HandlerInterceptorAdapter {

    private final static Logger logger = LoggerFactory.getLogger(RequestInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        long startTime = System.currentTimeMillis();
        request.setAttribute("visitstartTime", startTime);
        /**
         * 打印请求地址
         *  参数
         */
        String logStr = null ;
        if(request.getMethod().equals("GET")){
            logStr = JSON.toJSONString(request.getParameterMap());
        }else if(request.getMethod().equals("POST")){
            logStr = postInputStr(request) ;
            if("".equals(logStr)){
                logStr = JSON.toJSONString(request.getParameterMap());
            }
        }
        logger.info("request--->{}<--->{}<--->{}",
                request.getMethod(),request.getRequestURI(),logStr);

        return true;
    }

    public String postInputStr(HttpServletRequest request){

        String line = null;
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

        long startTime = (long) request.getAttribute("visitstartTime");
        request.removeAttribute("visitstartTime");
        long endTime = System.currentTimeMillis();
        String uri = request.getServletPath();
        System.out.println("本次请求处理时间为：" + uri + "---" + new Long(endTime - startTime) + "ms");
    }
}
