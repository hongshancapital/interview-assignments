package com.scdt.service;


public interface ShortPathService {
    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     * @param longPath 长路径,使用时需要加密,
     *                 例如aaa/bbb/ccc?abc=123
     *                 使用base64加密后为
     *                 YWFhL2JiYi9jY2MlM0ZhYmMlM0QxMjM=
     * @return 短路径
     */
    String getShortPath(String longPath);

    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息
     * @param shortPath 短路径
     * @return 长路径， 使用时需要解密
     */
    String getLongPath(String shortPath);
}


