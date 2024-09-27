package com.scdt.shortenurl.domain.service;

import com.scdt.shortenurl.common.exception.BizException;
import com.scdt.shortenurl.common.utils.EncodeUtils;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;

/**
 * @Description 摘要算法策略实现
 * @Author chenlipeng
 * @Date 2022/3/7 2:15 下午
 */
@Service("hashDigestStrategy")
public class HashDigestStrategy implements ShortenUrlStrategy {

    private static final String MD5_KEY = "chenlipeng";

    @Override
    public String genShortUrl(String url) {
        try {
            String[] dict = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
                    "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                    "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                    "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                    "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                    "U", "V", "W", "X", "Y", "Z"
            };
            String hex = EncodeUtils.encodeByMd5(url, MD5_KEY);
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String substring = hex.substring(8, 8 + 8);
            // Integer.parseInt()只能处理31位, 首位为符号位, 如果不用long, 则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(substring, 16);
            StringBuilder result = new StringBuilder();
            for (int j = 0; j < 6; j++) {
                // 把得到的值与0x0000003D=62进行位与运算, 计算在dict中的位置
                long index = 0x0000003D & lHexLong;
                result.append(dict[(int) index]);
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            return result.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new BizException("生成短链失败");
        }
    }


}
