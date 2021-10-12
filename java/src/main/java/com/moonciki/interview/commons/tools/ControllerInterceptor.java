package com.moonciki.interview.commons.tools;

import com.alibaba.fastjson.JSON;
import com.moonciki.interview.commons.constant.GlobalConstant;
import com.moonciki.interview.commons.exception.CustomException;
import com.moonciki.interview.commons.model.ResponseContent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一controller 请求拦截异常处理类
 */
@Slf4j
public abstract class ControllerInterceptor {

    @Autowired(required = false)
    private HttpServletResponse response;

    /**
     * 拦截逻辑
     * @param joinPoint
     * @return
     * @throws Exception
     */
    public Object invokePoint(ProceedingJoinPoint joinPoint) throws Exception{
        Signature sig = joinPoint.getSignature();

        MethodSignature msig = (MethodSignature) sig;
        Object target = joinPoint.getTarget();

        Method method = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());

        Object returnObj = null;

        String methodFullName = method.getDeclaringClass().getSimpleName() + "." + method.getName();

        Object[] args = joinPoint.getArgs();

        String paramsStr = showRequest(args);

        log.debug("[Request] : {} => {}", methodFullName, paramsStr);

        try {
            returnObj = joinPoint.proceed();
        } catch (Throwable te) {
            CustomException oe = null;

            if (te instanceof CustomException) {
                oe = (CustomException)te;

                log.debug("[ERROR CATCHED] - [" + methodFullName + "] : ", te);
            } else {
                oe = CustomException.createException(te);

                log.error("[ERROR UNCATCHED] - [" + methodFullName + "] : ", te);
            }

            Class returnClass = method.getReturnType();

            if(returnClass != null) {
                if (returnClass.isAssignableFrom(ResponseContent.class)) {
                    returnObj = ResponseContent.failResponse(oe);
                }
            }
        }
        String resultJson = "";
        if (returnObj != null) {
            resultJson = JSON.toJSONString(returnObj);

            if (returnObj instanceof ResponseContent) {
                ResponseContent resp = (ResponseContent) returnObj;

                String respCode = resp.getCode();

                setHttpStatus(respCode);
            }

        }

        log.debug("[Response] : {} => {}", methodFullName, resultJson);

        return returnObj;
    }

    /**
     * 设置返回码
     * @param respCode
     */
    private void setHttpStatus(String respCode){

        int httpStatus = 200;

        response.setStatus(httpStatus);

    }

    /**
     * 拼接请求参数
     * @param args
     * @return
     */
    private String showRequest(Object[] args) {
        String paramsJson = "";

        if(args != null && args.length > 0){
            //请求参数集合
            Map<String, Object> paramMap = new HashMap<>();

            for (int i=0; i<args.length; i++) {

                Object oneArg = args[i];
                if(oneArg == null){
                    continue;
                }

                if (!(oneArg instanceof BindingResult) && !(oneArg instanceof ModelMap) && !(oneArg instanceof Model)) {
                    boolean toJson = false;

                    String argClassName = oneArg.getClass().getName();

                    if(StringUtils.isNotBlank(argClassName)){
                        if(argClassName.startsWith(GlobalConstant.Base.base_package)){
                            toJson = true;
                        }else if (oneArg instanceof Iterable || oneArg instanceof Map) {
                            toJson = true;
                        }
                    }

                    String value = null;

                    if(toJson){
                        try {
                            value = JSON.toJSONString(oneArg);
                        } catch (Exception e) {
                            log.warn("Interceptor parse json ERROR:", e);
                        }
                    }else{
                        value = oneArg.toString();
                    }
                    paramMap.put("arg-" + i, value);
                }
            }
            paramsJson = JSON.toJSONString(paramMap);
        }
        return paramsJson;
    }

    public void getRequestParameters(HttpServletRequest request){
        Enumeration<?> enume = request.getParameterNames();

        if (null != enume) {
            Map<String, String> hpMap = new HashMap<String, String>();
            while (enume.hasMoreElements()) {
                Object element = enume.nextElement();
                if (null != element) {
                    String paramName = (String) element;
                    String paramValue = request.getParameter(paramName);
                    hpMap.put(paramName, paramValue);
                }
            }
        }
    }

}