package com.heyenan.shorturldemo.strategy.decorator;

import com.heyenan.shorturldemo.strategy.ShortUrlStrategy;
import com.heyenan.shorturldemo.untils.NumberTransform;

/**
 * @author heyenan
 * @description 具体装饰者修饰类 62位转换
 *
 * @date 2020/5/07
 */
public class To62Decorator extends MapDataDecorator {

    private ShortUrlStrategy strategy;

    public To62Decorator(ShortUrlStrategy strategy) {
        super(strategy);
    }

    @Override
    public String getShortUrl(String longUrl) {
        return mapDataDecorator(strategy.getShortUrl(longUrl));
    }

    @Override
    public String mapDataDecorator(String mapShortUrl) {
        return NumberTransform.convert10to62(Long.valueOf(mapShortUrl),6);
    }
}
