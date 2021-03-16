package com.sequoiacap.domain.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 短链映射状态
 */
@RequiredArgsConstructor
@Getter
public enum UrlMapStatus {

    /**
     * 可用状态
     */
    AVAILABLE(1),

    /**
     * 非法状态
     */
    INVALID(2),

    ;

    private final Integer value;
}
