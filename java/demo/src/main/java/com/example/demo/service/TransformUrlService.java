package com.example.demo.service;

/**
 * @ClassName TransformUrlService
 * @Description TODO 域名转换业务接口类
 * @Author 845627580@qq.com
 * @Version 1.0
 **/
public interface TransformUrlService {

    //短连接转长链接
    public String ShortUrlTransformLongUrl(String ShortUrl);


    //长链接转短链接
    public String LongUrlTransformShortUrl(String LongUrl);


}
