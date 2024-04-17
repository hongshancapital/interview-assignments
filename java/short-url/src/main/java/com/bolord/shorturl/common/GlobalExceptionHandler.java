package com.bolord.shorturl.common;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bolord.shorturl.exception.ShortUrlException;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ Exception.class })
    public Mono<Object> handleException(
            Exception ex,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        return handleExceptionInternal(
                ex,
                new Result<Void>(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务内部错误"),
                null,
                HttpStatus.INTERNAL_SERVER_ERROR,
                request,
                response
        );
    }

    @ExceptionHandler({ RuntimeException.class })
    public Mono<Object> handleRuntimeException(
            RuntimeException ex,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        return handleExceptionInternal(
                ex,
                new Result<Void>(false, HttpStatus.BAD_REQUEST.value(), "请求执行失败"),
                null,
                HttpStatus.BAD_REQUEST,
                request,
                response
        );
    }

    @ExceptionHandler({ ShortUrlException.class })
    public Mono<Object> handleShortUrlException(
            ShortUrlException ex,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        return handleExceptionInternal(
                ex,
                new Result<Void>(false, ex.getCode(), ex.getMessage()),
                null,
                HttpStatus.valueOf(ex.getCode()),
                request,
                response
        );
    }

    protected Mono<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        final String eid = UUID.randomUUID().toString().replaceAll("-", "");
        log.error("[捕获异常] - eid=" + eid, ex);

        response.setRawStatusCode(status.value());
        if (headers != null) {
            response.getHeaders().setAll(headers.toSingleValueMap());
        }

        return Mono.justOrEmpty(body);
    }

}
