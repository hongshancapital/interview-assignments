package com.shorturl.config;

import com.shorturl.utils.UrlUtils;
import com.shorturl.model.vo.ResponseMessage;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;

/**
 * Created by ruohanpan on 21/3/23.
 */
@Aspect
@Component
public class ParamPreProcessAspect {

    @Around("execution(* com.shorturl.controllers.ShortUrlController.urlCompress(String))")
    public Object preProcess(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        String url = (String) args[0];

        try {
            if (!UrlUtils.validateUrl(url)) {
                throw new MalformedURLException();
            }
            url = UrlUtils.normalizeUrl(url);
            return joinPoint.proceed(new Object[]{url});
        } catch (MalformedURLException e) {
            return new ResponseMessage("", url, false, "query url is not valid");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return new ResponseMessage("", url, false, "query got unknown error");
        }
    }
}
