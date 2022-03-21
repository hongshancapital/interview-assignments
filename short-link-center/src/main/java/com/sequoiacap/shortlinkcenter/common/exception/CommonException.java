/*
 *
 *  Copyright 2020 byai.com All right reserved. This software is the
 *  confidential and proprietary information of byai.com ("Confidential
 *  Information"). You shall not disclose such Confidential Information and shall
 *  use it only in accordance with the terms of the license agreement you entered
 *  into with byai.com.
 * /
 */

package com.sequoiacap.shortlinkcenter.common.exception;

/**
 * @author xiuyuan
 * @date 2022/3/17
 */
public class CommonException extends BaseException {

    public CommonException(int code, String message) {
        super(code, message);
    }

    public CommonException(int code, String message, Throwable e) {
        super(code, message, e);
    }

    public CommonException(int code, String message, String detailMsg) {
        super(code, message, detailMsg);
    }

    public CommonException(CommonErrorCodeEnum commonErrorCodeEnum) {
        super(commonErrorCodeEnum.getCode(), commonErrorCodeEnum.getMessage());
    }

    public CommonException(CommonErrorCodeEnum commonErrorCodeEnum, Throwable e) {
        super(commonErrorCodeEnum.getCode(), commonErrorCodeEnum.getMessage(), e);
    }
}
