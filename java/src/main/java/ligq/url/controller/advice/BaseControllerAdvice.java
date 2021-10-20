package ligq.url.controller.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

/**
 * api通用错误处理器
 * @author ligq
 * @since 2021-10-20
 */
@Slf4j
@ControllerAdvice("ligq.url.controller")
public class BaseControllerAdvice extends RequestBodyAdviceAdapter {

    @ExceptionHandler
    public ResponseEntity<?> exceptionHandle(Exception e) {
        log.error("Api Interface Occur Exception!", e);
        //设置请求头
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "text/plain");
        //设置响应状态
        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;
        ResponseEntity<?> entity = new ResponseEntity(e.getMessage(), headers, statusCode);
        return entity;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }
}
