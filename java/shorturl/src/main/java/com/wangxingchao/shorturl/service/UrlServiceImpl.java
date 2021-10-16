package com.wangxingchao.shorturl.service;

import com.wangxingchao.shorturl.exception.MaxLengthException;
import com.wangxingchao.shorturl.utils.NumberUtils;
import com.wangxingchao.shorturl.utils.Result;
import com.wangxingchao.shorturl.utils.enums.ResultErrorEnum;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
 *
 * 补充：
 * 没对域名格式进行校验，这块可以和前端小伙伴协商看具体情况是否对全域名进行处理
 * 假如全域名处理可使用正则截取或校验
 */
@Service
public class UrlServiceImpl implements UrlService {

    // 进制
    private static final int SCALE = 62;

    // 短码长度
    private static final int LENGTH = 8;

    // 长码长度限制
    private static final int LENGTH_LONG = 100;

    // 自增键 不放内存的话 可以考虑redis的自增 避免服务重启导致问题
    private static final AtomicInteger urlCount = new AtomicInteger(1);

    // 考虑多请求可能导致并发所以都是采用的线程安全的对象
    private static final Map<String, String> urlMap = new ConcurrentHashMap<>();

    // 校验长链接是否重复请求
    private static final Map<String, String> longUrlMap = new ConcurrentHashMap<>();

    @Override
    public Result short2long(String shortUrl) {
        if (shortUrl == null || shortUrl.length() != LENGTH) {
            return new Result(ResultErrorEnum.URL_PARAM_URL_ERROR);
        }
        return new Result(urlMap.get(shortUrl));
    }

    @Override
    public Result long2short(String longUrl) {
        // 长度以及参数校验
        if (longUrl == null || longUrl.length() > LENGTH_LONG) {
            return new Result(ResultErrorEnum.URL_PARAM_URL_ERROR);
        }
        // 自增键
        String shortUrl = longUrlMap.get(longUrl);
        if (shortUrl != null) {
            return new Result(shortUrl);
        }
        int increment = urlCount.getAndIncrement();
        try {
            // 将自增键转换为短链接 并进行存储
            shortUrl = NumberUtils.converter(increment, SCALE, LENGTH);
            urlMap.put(shortUrl, longUrl);
            longUrlMap.put(longUrl, shortUrl);
        } catch (MaxLengthException exception) {
            return new Result(ResultErrorEnum.URL_MAX_LENGTH_ERROR);
        }
        return new Result(shortUrl);
    }
}
