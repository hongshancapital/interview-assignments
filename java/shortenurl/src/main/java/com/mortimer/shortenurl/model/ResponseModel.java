package com.mortimer.shortenurl.model;

import com.mortimer.shortenurl.enums.ResponseStatusEnum;
import lombok.Getter;

@Getter
public class ResponseModel<T> {
    private int code;
    private String message;
    private T data;

    public ResponseModel(ResponseStatusEnum statusEnum, T data) {
        this.code = statusEnum.getCode();
        this.message = statusEnum.getMessage();
        this.data = data;
    }

    public static <T> ResponseModel<T> success(T data) {
        return new ResponseModel(ResponseStatusEnum.SUCCESS, data);
    }
}
