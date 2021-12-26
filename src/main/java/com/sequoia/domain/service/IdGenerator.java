package com.sequoia.domain.service;

public interface IdGenerator {
    /**
     * 生成下一个ID
     *
     * @return id
     */
    long nextId();
}
