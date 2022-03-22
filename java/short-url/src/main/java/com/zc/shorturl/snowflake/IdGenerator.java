package com.zc.shorturl.snowflake;

import com.zc.shorturl.snowflake.common.Result;

/**
 * Id Generator Interface
 */
public interface IdGenerator {
    public Result nextId();
}
