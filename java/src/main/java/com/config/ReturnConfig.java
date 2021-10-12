package com.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import static com.config.ResultStatus.EXECUTE_FAIL;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@EnableWebMvc
@Configuration
public class ReturnConfig {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @RestControllerAdvice("com.growing.talent")
    static class CommonResultResponseAdvice implements ResponseBodyAdvice<Object> {
        @Override
        public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
            return true;
        }

        @Override
        public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
            if (body != null && body.toString().toLowerCase().equals("false")) {
                ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
                HttpServletRequest requestInfo = attributes.getRequest();
                return Result.failure(EXECUTE_FAIL.getCode(), EXECUTE_FAIL.getMessage());
            }
            if (body instanceof Result) {
                return body;
            }
            return Result.success(body);
        }

    }

}
