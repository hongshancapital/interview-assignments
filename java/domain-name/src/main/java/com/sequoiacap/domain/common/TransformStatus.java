package com.sequoiacap.domain.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 短链转换状态
 */
@Getter
@RequiredArgsConstructor
public enum TransformStatus {

    /**
     * 转换成功
     */
    TRANSFORM_SUCCESS(1),

    /**
     * 转换失败
     */
    TRANSFORM_FAIL(2),

    /**
     * 重定向成功
     */
    REDIRECTION_SUCCESS(3),

    /**
     * 重定向失败
     */
    REDIRECTION_FAIL(4),

    ;

    private final Integer value;
}
