package com.github.shwas1.shorturl.service.generator;

/**
 * 短路径生成器接口
 *
 * @author zhouxin
 */
public interface ShortPathGenerator {
    /**
     * 生成一个唯一的短路径
     *
     * @return 生成的短路径，最大长度不超过8
     */
    String generate();
}
