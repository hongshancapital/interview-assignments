package com.interview.log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Date: 2021-12-17 13:55
 * @Description : 用于接口日志切面
 */
@Component
@Slf4j
@Aspect
public class WriteLoggerAspect {

    @Pointcut("execution( * com.interview.controller.*.*(..))")
    public void pointCut(){}

    @Around("pointCut()")
    public Object aroundAspectLogger(ProceedingJoinPoint pjp) throws Throwable {
        Signature signature=pjp.getSignature();
        WriteLog classAnnotation = (WriteLog) signature.getDeclaringType().getAnnotation(WriteLog.class);
        WriteLog methodAnnotation=null;
        MethodSignature mSig = null;
        if ((signature instanceof MethodSignature)) {
            mSig= (MethodSignature) signature;
            Method declaredMethod = pjp.getTarget().getClass().getDeclaredMethod(pjp.getSignature().getName(), mSig.getParameterTypes());
            methodAnnotation = declaredMethod.getAnnotation(WriteLog.class);
        }
        WriteLog finalAnnotation=methodAnnotation!=null?methodAnnotation:classAnnotation;

        String className=pjp.getTarget().getClass().getSimpleName();
        String methodName=signature.getName();
        long startTime=System.currentTimeMillis();
        Object result=null;
        try {
             result=pjp.proceed();
        }finally {
            JSONObject json=new JSONObject();
            json.put("logType", "promotionCoreApiMonitor");
            json.put("methodName",methodName);
            json.put("className", className);
            json.put("timeCosting", System.currentTimeMillis()-startTime);
            if(result==null){
                json.put("params",  buildArgs(pjp.getArgs()));
            }
            if(finalAnnotation!=null) {
                json.remove("logType");
                switch(finalAnnotation.logLevel()){
                    case Nothing:
                          break;
                    case Params:
                        json.put("params", buildArgs(pjp.getArgs()));
                        log.info("AOP===>{}", json.toJSONString());
                        break;
                    case Result:
                        json.put("params", buildArgs(pjp.getArgs()));
                        json.put("processResult", JSON.toJSONString(result));
                        log.info("AOP===>{}", json.toJSONString());
                        break;
                      default:
                          break;
                }

            }
        }
        return result;
    }

    private String buildArgs(Object[] args){
        if(args==null||args.length==0){
            return "";
        }
        List<Object> argList = Arrays.asList(args).stream().filter(arg ->
                (!(arg instanceof HttpServletResponse)&&(!(arg instanceof HttpServletRequest))
                )
        ).collect(Collectors.toList());
        return JSON.toJSONString(argList);
    }


}


