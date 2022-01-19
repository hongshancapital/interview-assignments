package com.example.shorturl.utils;

/**
 * @author yyp
 * @date 2022/1/16 10:15
 */
public interface BaseEnum<T, V> {
    T getCode();

    V getVal();
}
