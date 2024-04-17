package com.bolord.shorturl.generator;

/**
 * ID 生成器
 */
public interface IdGenerator {

    /**
     * 生成一个 ID，需要保证每次生成的不重复
     *
     * @return ID
     */
    long generateId();

}
