package com.example.aspect;

import com.example.bean.result.ApiResult;
import com.example.bean.result.ResultRpc;
import com.example.bean.result.ResultRpcEnum;
import com.example.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: wuxininfo
 * @date: 2019-06-14
 * @time: 14:44
 */
//@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class ServiceAroundAdvice {

    /**
     * @param proceedingJoinPoint
     * @return
     * @throws Throwable
     */
    @Around("com.example.aspect.PointcutConfiguration.accessService()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> returnType = method.getReturnType();
        Object result = null;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (IllegalArgumentException argEx) {
            result = handleException(returnType, argEx, ResultRpcEnum.RPC_PARAM_ERROR.getCode());
        } catch (BusinessException businessEx) {
            result = handleException(returnType, businessEx, businessEx.getCode() == null ? businessEx.getCode() : ResultRpcEnum.FAILED.getCode());
        } catch (Exception ex) {
            result = handleException(returnType, ex, ResultRpc.EXCEPTION, ResultRpc.EXCEPTION_MESSAGE);
        }finally {
        }
        return result;
    }

    /**
     * handleException without message
     *
     * @param returnType
     * @param ex
     * @param exceptionCode
     * @return
     */
    private Object handleException(Class<?> returnType, Exception ex, String exceptionCode) {
        return handleException(returnType, ex, exceptionCode, ex.getMessage());
    }

    /**
     * handleException with message
     *
     * @param returnType
     * @param ex
     * @param exceptionCode
     * @param message
     * @return
     */
    private Object handleException(Class<?> returnType, Exception ex, String exceptionCode, String message) {
        if (isReturnResult(returnType)) {
            return ApiResult.getErrorResultWithMsg(exceptionCode,message);
        }
        if (isReturnResultList(returnType)) {
            ArrayList<ApiResult> results = new ArrayList<>(1);
            results.add(ApiResult.getErrorResultWithMsg(exceptionCode, message));
            return results;
        }
        throw new RuntimeException(ex.getCause());
    }

    /**
     * 判断是否返回对象
     *
     * @param returnType
     * @return
     */
    private boolean isReturnResult(Class<?> returnType) {
        return returnType.isAssignableFrom(ResultRpc.class);
    }

    /**
     * 判断是否返回集合
     *
     * @param returnType
     * @return
     */
    private boolean isReturnResultList(Class<?> returnType) {
        return returnType.isAssignableFrom(ArrayList.class) || returnType.isAssignableFrom(List.class);
    }
}
