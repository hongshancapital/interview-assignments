package com.wangxingchao.shorturl.service;

import com.wangxingchao.shorturl.exception.MaxLengthException;
import com.wangxingchao.shorturl.utils.NumberUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 长码转短码
 * 实现思路：
 * 2种实现方式
 * 一种是自增算法，通过进制转换的方式将自增数字转换为更高进制（例如62进制）的短码格式，缺点是短码是连续的，可能造成安全隐患
 * 另一种是通过md5生成32位签名，截取成4段，任意取其中一个进行校验，假如不存在则保存成功，缺点是md5依旧有小概率发生重复
 *
 * 我这边采用的是第一种实现方式
 * 短码连续的问题可以通过修改进制字符顺序解决
 * @see com.wangxingchao.shorturl.utils.NumberUtils
 *
 * 短码转长码
 * 实现思路：
 * key-value存储，通过key（短码）获取到长码的地址
 * jvm内存的话，我直接使用的hashMap结构
 * 多服务的话可以使用redis的hash结构 + 一个自增的String类型的key实现
 */
@Service
public class UrlServiceImpl implements UrlService {

    // 进制
    private static final int SCALE = 62;

    // 短码长度
    private static final int LENGTH = 8;

    // 自增键 不放内存的话 可以考虑redis的自增 避免服务重启导致问题
    private static final AtomicInteger urlCount = new AtomicInteger(1);

    // 考虑多请求可能导致并发所以都是采用的线程安全的对象
    private static final Map<String, String> urlMap = new ConcurrentHashMap<>();

    // 校验长链接是否重复请求
    private static final Map<String, Object> longUrlMap = new ConcurrentHashMap<>();
    private static final Object VALUE = new Object();

    @Override
    public String short2long(String shortUrl) {
        return urlMap.get(shortUrl);
    }

    @Override
    public String long2short(String longUrl) {
        // 自增键
        if (longUrlMap.containsKey(longUrl)) {
            // 一般情况下会封装成对象供前端处理，包含成功标识、具体的错误信息等
            return "重复请求";
        } else {
            longUrlMap.put(longUrl, VALUE);
        }
        int increment = urlCount.getAndIncrement();
        String shortUrl;
        try {
            // 将自增键转换为短链接 并进行存储
            shortUrl = NumberUtils.converter(increment, SCALE, LENGTH);
            urlMap.put(shortUrl, longUrl);
        } catch (MaxLengthException exception) {
            return exception.getMessage();
        }
        return shortUrl;
    }
}
