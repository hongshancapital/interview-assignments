package com.sequoiacap.domain.common.lock;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 压缩码状态
 */
@RequiredArgsConstructor
@Getter
public enum CompressionCodeStatus {

    /**
     * 可用
     */
    AVAILABLE(1),

    /**
     * 已经使用
     */
    USED(2),

    /**
     * 非法
     */
    INVALID(3),

    ;

    private final Integer value;
}
