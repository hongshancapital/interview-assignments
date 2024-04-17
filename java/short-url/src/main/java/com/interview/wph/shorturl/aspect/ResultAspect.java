package com.interview.wph.shorturl.aspect;

import cn.hutool.core.date.SystemClock;
import com.interview.wph.shorturl.dto.ResponseDto;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * @author wangpenghao
 * @date 2022/4/18 17:07
 * controller层返回结果,切面
 */
@Aspect
@Component
public class ResultAspect {
    /**
     * 方法切点
     */
    @Pointcut(
            "@annotation(org.springframework.web.bind.annotation.RequestMapping) ||" +
                    "@annotation(org.springframework.web.bind.annotation.GetMapping) ||" +
                    "@annotation(org.springframework.web.bind.annotation.PostMapping) ||" +
                    "@annotation(org.springframework.web.bind.annotation.PutMapping) ||" +
                    "@annotation(org.springframework.web.bind.annotation.PatchMapping) ||" +
                    "@annotation(org.springframework.web.bind.annotation.DeleteMapping)")
    public void executeService() {
    }

    @Around("executeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        return setCostTime(pjp);
    }

    private Object setCostTime(ProceedingJoinPoint pjp) throws Throwable {
        long startMills = SystemClock.now();
        Object result = pjp.proceed();
        ResponseDto responseDto;
        if (result instanceof ResponseDto){
            responseDto = (ResponseDto) result;
            responseDto.setCostTime(SystemClock.now() - startMills);
            return responseDto;
        }
        return result;
    }
}
