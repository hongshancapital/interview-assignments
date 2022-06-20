package com.heyenan.shorturldemo.strategy.decorator;

import com.heyenan.shorturldemo.strategy.ShortUrlStrategy;
import com.heyenan.shorturldemo.untils.NumberTransform;
import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author heyenan
 * @description 具体装饰者 Md5加密处理
 *
 * @date 2020/5/07
 */
public class Md5Decorator extends MapDataDecorator{

    private ShortUrlStrategy strategy;

    public Md5Decorator(ShortUrlStrategy strategy) {
        super(strategy);
    }

    @Override
    public String getShortUrl(String longUrl) {
        return mapDataDecorator(strategy.getShortUrl(longUrl));
    }

    @Override
    public String mapDataDecorator(String mapShortUrl) {
        return encode(mapShortUrl);
    }

    public static String encode(byte[] data) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        md5.update(data, 0, data.length);
        return new String(Hex.encodeHex(md5.digest()));
    }

    public static String encode(String data) {
        return encode(data.getBytes(StandardCharsets.UTF_8));
    }

}
