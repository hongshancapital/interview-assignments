package com.hszb.shorturl.common.enums;

import lombok.AllArgsConstructor;

/**
 * @Author: xxx
 * @License: (C) Copyright 2005-2019, xxx Corporation Limited.
 * @Date: 2021/12/19 1:33 下午
 * @Version: 1.0
 * @Description:
 */

@AllArgsConstructor
public enum ResponseCodeMsg {

    APP_NO_RIGHT(-9999, "当前APPID没有权限"),
    PARAM_IS_NULL(10000, "请求参数缺失"),
    SHORT_URL_FULL(10001, "短地址已满，无法生成"),
    INVALID_URL (10002, "无效地址");

    /** 错误码 */
    public Integer code;

    /** 错误描述 */
    public String value;
}
