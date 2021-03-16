package com.sequoiacap.domain.common.lock;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 分布式锁KEY
 */
@RequiredArgsConstructor
@Getter
public enum LockKey {

    /**
     * 创建短链映射场景
     */
    CREATE_URL_MAP("octopus:url:map:create", "创建URL映射", 0L, 10000L);

    private final String code;
    private final String desc;
    private final long waitTime;
    private final long releaseTime;
}
