package com.yofei.shortlink.exception;

import com.yofei.shortlink.common.BadParamException;
import com.yofei.shortlink.dto.PlainResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@Slf4j
@ControllerAdvice
public class WebExceptionHandler {


    @ExceptionHandler({BadParamException.class, RuntimeException.class, Exception.class})
    public ResponseEntity<PlainResponse> handleExceptions(final HttpServletRequest request,
                                                          final HttpServletResponse response,
                                                          final Exception exception) {
        log.error("[BaseException] RequestURI : [{}] {}\tRemoteAddr : {}",
                  request.getMethod(),
                  request.getRequestURI(),
                  request.getRemoteAddr(),
                  exception);

        return createResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<PlainResponse> createResponseEntity(@NonNull final Exception exception, @NonNull final HttpStatus status) {
        return createResponseEntity(exception, status.value());
    }

    private ResponseEntity<PlainResponse> createResponseEntity(@NonNull final Exception exception, final int status) {
        return createResponseEntity(exception.getMessage(), status);
    }

    private ResponseEntity<PlainResponse> createResponseEntity(@NonNull final String errorMessage, final int status) {
        return ResponseEntity.status(status).body(PlainResponse.failure(errorMessage));
    }
}
