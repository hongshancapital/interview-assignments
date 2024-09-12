package com.heyenan.shorturldemo.strategy.decorator;

import com.heyenan.shorturldemo.strategy.ShortUrlStrategy;

/**
 * @author heyenan
 * @description 装饰者超类
 *
 * @date 2020/5/07
 */
public abstract class  MapDataDecorator implements ShortUrlStrategy {

    /** 具体策略*/
    private ShortUrlStrategy strategy;

    /** 封装策略 */
    public MapDataDecorator(ShortUrlStrategy strategy){
        this.strategy = strategy;
    }
    /** 获取短域名 */
    public abstract String getShortUrl(String longUrl);

    /** 装饰者类映射数据处理 */
    public abstract String mapDataDecorator(String mapShortUrl);


}
