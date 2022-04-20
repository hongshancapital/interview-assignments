package pers.zhufan.shorturl.generator;

import pers.zhufan.shorturl.domain.shorturl.ShorterUrl;
import pers.zhufan.shorturl.domain.shorturl.ShorterWithSimpleString;

/**
 * @Author zhufan
 * @Date 2022-04-18 19:50
 * @ClassName UrlShorterGenerator
 * @Description 长链接转换为短链接生成器
 */
public interface UrlShorterGenerator {


    /**
     * @Author zhufan
     * @Date 2022-04-18 20:15
     * @Description //长链接转换为短链接执行方法
     **/
    ShorterUrl generate(String key, int lenth, String url);

}
