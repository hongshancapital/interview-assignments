package com.sequoiacap.tinyurl.service;

import com.sequoiacap.tinyurl.exception.BadParamException;

/**
 * 短码生成器
 */
public interface IdGenerator {
    String nextId(String url);

    /**
     * 校验当前id生成器生成的id是否合法
     *
     * @param id
     * @throws BadParamException
     */
    void checkId(String id) throws BadParamException;
}
