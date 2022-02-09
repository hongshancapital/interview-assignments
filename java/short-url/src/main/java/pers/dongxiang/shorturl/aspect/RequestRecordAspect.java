package pers.dongxiang.shorturl.aspect;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @ProjectName short-url
 * @Package pers.dongxiang.shorturl.aspect
 * @ClassName RequestRecordAspect
 * @Description 请求记录
 * @Company lab
 * @Author dongxiang
 * @Date 11/1/2021 8:43 PM
 * @UpdateUser
 * @UpdateDate
 * @UpdateRemark
 * @Version 1.0.0
 */
@Component
@Aspect
public class RequestRecordAspect {

    @Pointcut("execution(public * pers.dongxiang.shorturl.controller.ShortUrlController.*(..))")
    public void operationLog(){}

    @Around("operationLog()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        LogInfo logInfo = new LogInfo();
        logInfo.setUrl(request.getRequestURL().toString());
        logInfo.setMethod(request.getMethod());
        logInfo.setArgs(Arrays.toString(joinPoint.getArgs()));

        try {
            Object res = joinPoint.proceed();

            logInfo.setResult(res.toString());
            // TODO 写入kafka,rocketmq之类,同步到日志

            return res;
        } finally {
            logInfo = null;
        }
    }

    @Data
    static class LogInfo{
        private String url;
        private String method;
        private String args;
        private String result;
    }

}
