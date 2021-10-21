package com.shenshen.zhw.urlconverter.service;

public interface ConvertService {

    /**
     * 保存长url地址，返回短地址
     * @param longUrl 长url地址
     * @return 短地址标识
     */
    String save(String longUrl);

    /**
     * 获取长url地址
     * @param shortUrl 短地址标识
     * @return 长url地址
     */
    String get(String shortUrl);

}
