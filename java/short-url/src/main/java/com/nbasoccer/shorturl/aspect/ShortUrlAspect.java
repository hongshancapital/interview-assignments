package com.nbasoccer.shorturl.aspect;

import com.nbasoccer.shorturl.dto.ResponseCode;
import com.nbasoccer.shorturl.dto.Result;
import com.nbasoccer.shorturl.utils.UrlRegUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Aspect//声明切面
@Component//注入到容器
public class ShortUrlAspect {

    /**
     * 定义切入点，切入点为com.example.aop下的所有函数
     */
    @Pointcut("execution(public * com.nbasoccer.shorturl.controller.ShortUrlController.*(..))")
    public void webLog(){}

    /**
     * 环绕通知：切整个包下面的所有涉及到调用的方法的信息
     * @param joinPoint
     * @throws Throwable
     */
    @Around("webLog()")
    public Result doAround(ProceedingJoinPoint joinPoint){
        Long beginTime = System.currentTimeMillis();
        Result result = new Result();
        try {
            String paramName = getParamName(joinPoint.getSignature().getName());
            if(isParamIsNUll(paramName)) {
                result = new Result(ResponseCode.PARAM_IS_NULL);
                return result;
            }
            if(isInvalidUrl(paramName)) {
                result = new Result(ResponseCode.INVALID_URL);
                return result;
            }
            result = new Result(ResponseCode.SUCCESS);
            result.setData(joinPoint.proceed());
        }catch (Throwable throwable) {
            result.setCode(ResponseCode.SERVER_ERROR.getCode());
            result.setMessage(ResponseCode.SERVER_ERROR.getMessage() + ":" + throwable.getMessage());
        } finally {
            Long endTime = System.currentTimeMillis();
            Long took = endTime - beginTime;
            result.setTook(took);
        }
        return result;

    }

    private String getParamName(String methodName){
        String paramName = "";
        if("create".equals(methodName)) paramName = "longUrl";
        if("query".equals(methodName)) paramName = "shortUrl";
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        return request.getParameter(paramName);
    }

    private Boolean isParamIsNUll(String parameter){
        return StringUtils.isBlank(parameter);
    }

    private Boolean isInvalidUrl(String parameter){
        return !UrlRegUtils.isValidUrl(parameter);
    }
}
