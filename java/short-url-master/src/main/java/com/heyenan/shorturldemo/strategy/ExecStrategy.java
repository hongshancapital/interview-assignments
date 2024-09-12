package com.heyenan.shorturldemo.strategy;

/**
 * @author heyenan
 * @description 映射短域名策略模式执行类
 *
 * @date 2020/5/07
 */

public class ExecStrategy {

    /*** 定义映射策略*/
    private ShortUrlStrategy strategy;

    /**
     * 赋值具体策略
     */
    public ExecStrategy(ShortUrlStrategy strategy) {

        this.strategy = strategy;
    }

    /**
     * 获取短连接
     *
     * @param longUrl 长链接
     * @return 短链接
     */
    public String getShortUrl(String longUrl) {

        return this.strategy.getShortUrl(longUrl);
    }

}
