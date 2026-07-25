package org.zxl.advise;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class CommonResponse<T> {
    private long timestamp;
    private int status;
    private int code = 0;
    private String message;
    private String path;
    private T entity;

    public static <T> CommonResponse ok(T entity) {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.entity = entity;
        commonResponse.timestamp = System.currentTimeMillis();
        commonResponse.status = HttpStatus.OK.value();
        return commonResponse;
    }

    public static CommonResponse ok() {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.timestamp = System.currentTimeMillis();
        commonResponse.status = HttpStatus.OK.value();
        return commonResponse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}
