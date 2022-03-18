package com.yofei.shortlink.dto;

import lombok.Getter;

@Getter
public abstract class BaseResponse {

    protected boolean success;

    protected String errorMsg;
}