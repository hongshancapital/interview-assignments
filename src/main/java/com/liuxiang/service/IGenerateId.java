package com.liuxiang.service;

/**
 * @author liuxiang6
 * @date 2022-01-06
 **/
public interface IGenerateId {

    /**
     * 当前生成id的方式
     * @return
     */
    String generateType();

    /**
     * 具体实现方法
     * @param longUrl
     * @return
     */
    String generate(String longUrl);
}
