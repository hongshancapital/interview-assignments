package url.converter.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import url.converter.common.ParamValidator;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * 日志打印切面
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("execution(* url.converter.facade.controller.*.*(..))")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object doLog(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = null;
        String methodName = null;
        Object result;
        try {
            args = pjp.getArgs();
            Signature signature = pjp.getSignature();
            methodName = new StringBuilder(signature.getDeclaringTypeName()).append(".")
                    .append(signature.getName()).toString();
        } catch (Exception e) {
            log.info("LogAspect.doLog, 参数日志解析异常");
        }

        long startTime = System.currentTimeMillis();
        if (args != null && args.length > 0 && Objects.nonNull(args[0])) {
            ParamValidator.validate(args[0]);
        }
        result = pjp.proceed();
        long endTime = System.currentTimeMillis();
        log.info("LogAspect.doLog, method succeed: method={}, req={}, res={}, spentTime={}", methodName, args,
                result, endTime - startTime);
        return result;
    }
}
