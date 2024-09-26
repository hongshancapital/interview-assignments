package com.example.sequoiahomework.service;

/**
 * @author Irvin
 * @description 改变url的接口
 * @date 2021/10/9 19:45
 */
public interface ChangeUrlService {

    /**
     * 长域名转短域名
     *
     * @param oriUrl 接口入参，长域名
     * @return java.lang.String
     * @author Irvin
     * @date 2021/10/9
     */
    String longToShort(String oriUrl);

    /**
     * 根据短域名获得长域名
     *
     * @param oriUrl 接口入参，短域名
     * @return java.lang.String
     * @author Irvin
     * @date 2021/10/9
     */
    String shortToLong(String oriUrl);
}
