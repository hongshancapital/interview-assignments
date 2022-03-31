package com.domain.utils.web.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.domain.api.response.BaseResponse;
import com.domain.utils.CacheUtils;
import com.domain.utils.web.annotation.ApiReSubmit;
import com.domain.utils.web.enums.HttpResponseCodeEnum;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
/**
 * 重复提交spring mvc AOP代理
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
@Aspect
@Component
public class ResubmitRequestAspect {

    @Autowired(required = false)
    private CacheUtils cacheUtils; //限流缓存

    /**
     * 定义切入点
     */
    @Pointcut("execution(public * com.*.api.controller.*..*.*(..))")
    public void controllerPoint(){

    }

    /**
     * 环绕通知
     * 说明：将HTTP请求作 限制时间内的唯一请求KEY 作为不可重复标志判断是否重复提交
     */
    @Around("controllerPoint()")
    public Object doAround(ProceedingJoinPoint joinPoint)  {
        try{
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            ApiReSubmit annotation = AnnotationUtils.findAnnotation(method,ApiReSubmit.class);
            ServletRequestAttributes attributes= (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if(cacheUtils!=null&&annotation!=null&&attributes!=null){ //判断重复提交

                //收集请求  客户端地址+服务端地址+http方法+http请求体
                HttpServletRequest request=attributes.getRequest();
                JSONObject requestInfo=new JSONObject();

                String url=request.getRequestURI();
                String httpMethod=request.getMethod();
                String clientIp=request.getRemoteAddr();
                requestInfo.put("url",url);
                requestInfo.put("httpMethod",httpMethod);
                requestInfo.put("clientIp",clientIp);



                JSONObject context=new JSONObject();
                if(httpMethod.equalsIgnoreCase("POST")){
                    if(joinPoint.getArgs().length>0){
                        JSONObject jsonObject =new JSONObject();
                        for(Object o:joinPoint.getArgs()){
                            if(o instanceof HttpServletRequest || o instanceof HttpServletResponse) continue;
                            jsonObject.put(o.getClass().getName(), JSON.toJSONString(o));
                        }
                        context=jsonObject;
                    }
                }
                else if(httpMethod.equalsIgnoreCase("GET")){
                    int idx=request.getRequestURI().indexOf("?");
                    if(idx!=-1){
                        JSONObject jsonObject =new JSONObject();
                        String paras=request.getRequestURI().substring(idx+1);
                        String[] strings=paras.split("\\&");
                        for(String string:strings){
                            String[] key_valus=string.split("\\=");
                            if(key_valus.length>=2){
                                jsonObject.put(key_valus[0],key_valus[1]);
                            }
                        }
                        context=jsonObject;
                    }
                }
                requestInfo.put("body",context);

                String key=requestInfo.toJSONString();
                if(cacheUtils.resubmit(key)){
                    return BaseResponse.buildFail(HttpResponseCodeEnum.TRYAGAIN);  //重复提交
                }
                cacheUtils.submit(key);
            }
        }catch (Throwable t){
           t.printStackTrace();
        }
        //未重复/不需要判断重复提交 正常执行spring mvc流程
        Object returnValue=null;
        try{
            returnValue=joinPoint.proceed();
        }catch (Throwable t){
            throw new RuntimeException(t);
        }
        return returnValue;
    }
}
