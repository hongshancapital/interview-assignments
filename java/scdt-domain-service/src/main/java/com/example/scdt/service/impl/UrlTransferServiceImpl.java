package com.example.scdt.service.impl;

import com.example.scdt.exception.ApplicationException;
import com.example.scdt.service.UrlTransferService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * 长短域名转换接口实现
 */
@Slf4j
@Service
public class UrlTransferServiceImpl implements UrlTransferService {

    // 前缀标记：便于使用短域名访问时进行区分
    private final static String SHORT_URL_PREFIX = "SHORT_URL";

    // 短域名组成元字符集（共 62 个）
    private final static String[] META_CHARSET = new String[]{
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };

    // 映射存储集合
    private static Map<String, String> urlMap = new HashMap<String, String>();

    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     *
     * @param longUrl 长域名
     * @return 短域名
     */
    @Override
    public String longUrlToShortUrl(String longUrl) {
        return transToShortUrl(longUrl);
    }

    /**
     * 生成短域名算法实现：
     * 1. 将长网址 md5 生成 32 位签名串,分为 4 段, 每段 8 个字节
     * 2. 对这四段循环处理, 取 8 个字节, 将他看成 16 进制串与 0x3FFFFFFF(30位1) 与操作, 即超过 30 位的忽略处理
     * 3. 这 30 位分成 6 段, 每 5 位的数字作为字母表的索引取得特定字符, 依次进行获得 6 位字符串
     * 4. 总的 md5 串可以获得 4 个 6 位串,取里面的任意一个就可作为这个长 url 的短 url 地址
     * <p>
     * 【备注】算法原理介绍见：https://hufangyun.com/2017/short-url/?hmsr=toutiao.io&utm_medium=toutiao.io&utm_source=toutiao.io
     *
     * @param longUrl
     * @return
     */
    private String transToShortUrl(final String longUrl) {
        // 1. 将长域名转为 MD5 序列
        final String md5Hex = DigestUtils.md5Hex(longUrl);
        String[] shortUrls = new String[4];

        // 2. 分段运算
        for (int i = 0; i < 4; i++) {
            StringBuffer strBuf = new StringBuffer();
            // 2.1 将MD5总长32位字符串，分为4组，每组8位
            String subMd5Str = md5Hex.substring(i * 8, i * 8 + 8);
            // 2.2 4个子串，将每一个子串看成 16 进制 与 0x3FFFFFFF(30位1) 进行与运算
            // 0x3FFFFFFF --2进制--> 0011 1111 1111 1111 1111 1111 1111 1111
            long temp = 0x3FFFFFFF & Long.parseLong(subMd5Str, 16);
            // 3. 分段，获取元字符
            // 每次取5位作为索引: 0011 1101 ----> 最大代表10进制 61（元字符共61个）
            for (int j = 0; j < 6; j++) {
                int index = (int) (0x0000003D & temp);
                // 根据索引取出元字符
                strBuf.append(META_CHARSET[index]);
                // 继续下一个分组
                temp = temp >> 5;
            }
            shortUrls[i] = strBuf.toString();
        }
        // 4. 随机选取一个作为短域名结果
        String shorUrl = shortUrls[new Random().nextInt(4)];
        // 存储短域名，同时建立长短域名的映射关系
        urlMap.put(SHORT_URL_PREFIX + shorUrl, longUrl);
        return shorUrl;
    }

    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息
     *
     * @param shortUrl 短域名
     * @return 长域名
     */
    @Override
    public String shortUrlToLongUrl(String shortUrl) {
        String longUrl = urlMap.get(SHORT_URL_PREFIX + shortUrl);
        if (StringUtils.isEmpty(longUrl)) {
            log.error("根据短域名 - " + shortUrl + " - 读取长域名信息失败。");
            // 实际应用中可以定义异常抛出，并在 controller 层面对异常做统一处理
//            throw new ApplicationException("读取长域名信息失败", 100001);
        }

        return longUrl;
    }
}
