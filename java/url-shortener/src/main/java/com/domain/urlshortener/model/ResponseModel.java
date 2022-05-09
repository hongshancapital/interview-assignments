package com.domain.urlshortener.model;

import com.domain.urlshortener.enums.BizStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * @author: rocky.hu
 * @date: 2022/4/1 21:00
 */
@Schema(description = "响应实体")
@Getter
public class ResponseModel<T> {

    @Schema(description = "状态码")
    private String code;
    @Schema(description = "状态描述")
    private String message;
    @Schema(description = "数据")
    private T data;

    public ResponseModel() {
    }

    public ResponseModel(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseModel(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResponseModel(BizStatus status) {
        this.code = status.getCode();
        this.message = status.getDescription();
    }

    public ResponseModel(BizStatus status, T data) {
        this.code = status.getCode();
        this.message = status.getDescription();
        this.data = data;
    }

    public static ResponseModel ok() {
        return new ResponseModel<>(BizStatus.SUCCESS);
    }

    public static <T> ResponseModel<T> ok(T data) {
        return new ResponseModel<T>(BizStatus.SUCCESS, data);
    }

    public static ResponseModel fail() {
        return new ResponseModel<>(BizStatus.FAILED);
    }

    public static <T> ResponseModel<T> fail(T data) {
        return new ResponseModel<T>(BizStatus.FAILED, data);
    }

    public static ResponseModel fail(BizStatus bizStatus) {
        return new ResponseModel(bizStatus);
    }

    public static ResponseModel fail(BizStatus bizStatus, String message) {
        return new ResponseModel(bizStatus.getCode(), message);
    }

}
