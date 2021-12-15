package com.example.serv;

/**
 * @projectName: shortUrl
 * @package: com.example.serv
 * @className: IShortUrlServ
 * @description: 短域名服务接口
 * @author: Kai
 * @version: v1.0
 */
public interface IShortUrlServ {
    /**
     * 接收长域名信息返回解析后对应的短域名信息
     *
     * @param url 长域名信息
     * @return    短域名信息
     */
    String transLong(String url);
}
