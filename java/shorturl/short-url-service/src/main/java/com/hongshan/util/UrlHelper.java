package com.hongshan.util;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public class UrlHelper {

    //Caffeine是guava的升级版本, 因使用了 Window TinyLfu 回收策略，提供了一个近乎最佳的命中率
    public static final Cache<String, String> cache = Caffeine.newBuilder()
            .maximumSize(1000000)//100W个缓存
            .expireAfterWrite(7, TimeUnit.DAYS)//7天过期
            .build();

    //2^30 - 1   = 0x3FFFFFFF
    private static final int INF_30 = 0x3FFFFFFF;

    //61 = 0x0000003D
    private static final int INF_D = 0x0000003D;

    //获取短域名的基数
    private static final char[] metas = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
            'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
            'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};


    /**
     * 短域名处理方法
     *
     * @param commonUrl      长域名
     * @param shortUrlLength 短域名长度
     * @return
     * @throws Exception
     */
    public static String handleShortUrl(String commonUrl, int shortUrlLength) throws Exception {
        if (shortUrlLength < 4 || shortUrlLength > 8) {
            throw new Exception("需要生成的短域名长度必须在以下范围：4<= length <=8");
        }
        String cacheShortUrl = cache.getIfPresent(commonUrl);
        if (StringUtils.isBlank(cacheShortUrl)) {
            Random random = new Random();
            //每次随机生成两个秘钥key,分别拼接到头部和尾部
            StringBuilder key1_Builder = new StringBuilder();
            StringBuilder key2_Builder = new StringBuilder();
            for (int i = 0; i < shortUrlLength / 2; i++) {
                key1_Builder.append(metas[random.nextInt(metas.length)]);
                key2_Builder.append(metas[random.nextInt(metas.length)]);
            }
            //原始域名进行MD5得到32位的字符串
            String md5Code = DigestUtils.md5DigestAsHex(key1_Builder.append(commonUrl.trim()).append(key2_Builder).toString().getBytes());
            //会生成4个长度为shortUrlLength的短连接，随机选择一个
            List<String> resUrlList = new ArrayList<>();
            //分成4短，每段8位字符串
            for (int i = 0; i < 4; i++) {
                String subString = StringUtils.substring(md5Code, i * 8, (i + 1) * 8);
                // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用long ，则会越界
                long hexLong = INF_30 & Long.parseLong(subString, 16);
                StringBuilder sbBuilder = new StringBuilder();
                for (int j = 0; j < shortUrlLength; j++) {
                    // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                    long index = INF_D & hexLong;
                    // 把取得的字符拼接
                    sbBuilder.append(metas[(int) index]);
                    // 每次循环按位右移 shortUrlLength / 2 位
                    hexLong = hexLong >> (shortUrlLength / 2);
                }
                resUrlList.add(sbBuilder.toString());
            }
            cacheShortUrl = resUrlList.get(random.nextInt(4));
            cache.put(commonUrl, cacheShortUrl);
            cache.put(cacheShortUrl, commonUrl);
        }
        return cacheShortUrl;
    }

}
