package pers.zhufan.shorturl.storage;

import pers.zhufan.shorturl.domain.shorturl.ShorterUrl;

/**
 * 用来存储字符串短地址,后期适配不同的存储器
 */
public interface ShorterStorage {

    String get(String shorter);

    void clean(String url);

    void cleanShorter(String shorter);

    void save(String url, ShorterUrl shorter);

    void clean();

}
