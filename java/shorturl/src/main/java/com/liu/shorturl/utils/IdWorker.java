package com.liu.shorturl.utils;

/**
 * Description： ID生成器
 * Author: liujiao
 * Date: Created in 2021/11/11 17:49
 * email: liujiao@fcbox.com
 * Version: 0.0.1
 */
public interface IdWorker<T> {

    /**
     * 生成下一个ID
     * @return
     */
    T nextId();
}
