package com.sequoiacap.domain.common;

/**
 * 序列生成器
 */
public interface SequenceGenerator {

    /**
     * 生成整型序列
     *
     * @return long
     */
    long generate();
}
