package com.ccb.domain.generate;


/**
 * @Author: nieyy
 * @Date: 2021/7/24 14:16
 * @Version 1.0
 * @Description: 生成短域名
 */

public interface IDomainShorterGenerator {


    /**
     * 生成一个短域名对象
     *
     * @param url
     * @return
     */
    String generate(int length);

}
