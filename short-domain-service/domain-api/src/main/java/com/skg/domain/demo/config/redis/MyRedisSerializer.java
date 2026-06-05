package com.skg.domain.demo.config.redis;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @Author:liujw
 * @DateTime:2020/5/20 16:13
 * @Description:
 */
public class MyRedisSerializer implements RedisSerializer<String> {
    private final Charset charset;
    public static final StringRedisSerializer US_ASCII;
    public static final StringRedisSerializer ISO_8859_1;
    public static final StringRedisSerializer UTF_8;
    private String prefix;

    public MyRedisSerializer() {
        this(StandardCharsets.UTF_8);
    }

    public MyRedisSerializer(String prefix) {
        this(StandardCharsets.UTF_8);
        this.prefix = prefix;
    }

    public MyRedisSerializer(Charset charset) {
        Assert.notNull(charset, "Charset must not be null!");
        this.charset = charset;
    }

    public String deserialize(@Nullable byte[] bytes) {
        String key = bytes == null ? null : new String(bytes, this.charset);
        if (key == null) {
            return null;
        }

        if (this.prefix != null) {
            Integer indexOf = key.indexOf(this.prefix);
            if (indexOf.equals(0)) {
                key = key.substring(this.prefix.length());
            }
        }

        return key;
    }

    public byte[] serialize(@Nullable String string) {
        final String prefixString = this.prefix == null ? "" : this.prefix;
        return string == null ? null : (prefixString + string).getBytes(this.charset);
    }

    static {
        US_ASCII = new StringRedisSerializer(StandardCharsets.US_ASCII);
        ISO_8859_1 = new StringRedisSerializer(StandardCharsets.ISO_8859_1);
        UTF_8 = new StringRedisSerializer(StandardCharsets.UTF_8);
    }


}
