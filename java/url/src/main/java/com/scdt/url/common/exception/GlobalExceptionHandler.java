package com.scdt.url.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;
import java.util.stream.Collectors;

import static com.scdt.url.common.exception.ErrorDetail.from;
import static org.apache.commons.lang3.StringUtils.isEmpty;


@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ResponseEntity<?> handleAppException(AppException ex, ServerWebExchange exchange) {
        log.error("App error:", ex);
        ServerHttpRequest request = exchange.getRequest();
        ErrorRepresentation representation = ErrorRepresentation.from(from(ex, request.getURI().toString()));
        return new ResponseEntity<>(representation, new HttpHeaders(), representation.httpStatus());
    }

    @ExceptionHandler({WebExchangeBindException.class})
    @ResponseBody
    public ResponseEntity<ErrorRepresentation> handleInvalidRequest(WebExchangeBindException ex, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().toString();

        Map<String, Object> error = ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, fieldError -> {
                    String message = fieldError.getDefaultMessage();
                    return isEmpty(message) ? "无错误提示" : message;
                }));

        log.error("Validation error for [{}]:{}", ex.getMethodParameter().getParameterType().getName(), error);
        ErrorRepresentation representation = ErrorRepresentation.from(from(new RequestValidationException(error), path));
        return new ResponseEntity<>(representation, new HttpHeaders(), representation.httpStatus());
    }


    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<?> handleGeneralException(Throwable ex, ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().toString();

        log.error("Error occurred while access[{}]:", path, ex);
        ErrorRepresentation representation = ErrorRepresentation.from(from(new SystemException(ex), path));
        return new ResponseEntity<>(representation, new HttpHeaders(), representation.httpStatus());
    }


}
