package com.scdt.china.shorturl.util;

/**
 * 接口
 *
 * @author：costa
 * @date：Created in 2022/4/12 9:26
 */
public interface Computable<K, V> {
    V compute(K arg) throws InterruptedException;
}
