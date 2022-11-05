package com.interview.wph.shorturl.common.consts;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wangpenghao
 * @date 2022/4/21 13:35
 */
@Component
//@Data
public class SpringConst {
    @Value("${short.url.max_len:8}")
    Integer maxShortLen;

    @Value("${valid.cache.size:1000}")
    Integer validCacheSize;

    @Value("${invalid.cache.size:1000}")
    Integer invalidCacheSize;

    public Integer getMaxShortLen() {
        return maxShortLen;
    }

    public Integer getValidCacheSize() {
        return validCacheSize;
    }

    public Integer getInvalidCacheSize() {
        return invalidCacheSize;
    }
}
