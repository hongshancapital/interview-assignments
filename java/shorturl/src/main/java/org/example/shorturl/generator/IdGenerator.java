package org.example.shorturl.generator;

/**
 * id生成器,通过实现该接口可生成不同的雪花id生成器,默认使用最简单的指定方式
 *
 * @author bai
 * @date 2021/6/19 0:50
 */
public interface IdGenerator {
    /**
     * 获取数值型分布式Id。
     *
     * @return 生成后的Id。
     */
    long nextLongId();
}
