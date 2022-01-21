package com.layne.interview.util;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * 生成短域名工具类
 *
 * @author layne
 * @version UrlManage.java, v 0.1 2022/1/17 23:21 下午
 */
public class DigestUtil {

    /**
     * 用来拼短域名的char数组，此数组越大，短域名重复几率越低
     */
    private static final char[] HEX_CHARS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                    'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                    'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                    'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 生成md5时url前面需要加的key值，防止url过短
     */
    private static final String MD5_KEY = "md5_gen_key";


    public static String encodeHex(String longUrl) {
        // 获取需要进行MD5计算的字符串
        byte[] bytes = (MD5_KEY + longUrl).getBytes(StandardCharsets.UTF_8);

        // 使用md5获取一个32长度的md5串
        String md5Hex = DigestUtils.md5DigestAsHex(bytes);

        // 这里默认使用32位md5码的最后八位
        String subString = md5Hex.substring(24, 32);

        // 这里需要使用 long 型来转换，因为 Integer.parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用long ，则会越界
        long hexLong = 0x3FFFFFFF & Long.parseLong(subString, 16);

        StringBuilder outChars = new StringBuilder();
        for (int j = 0; j < 8; j++) {

            // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
            long index = 0x0000003D & hexLong;

            // 把取得的字符相加
            outChars.append(HEX_CHARS[(int) index]);

            // 每次循环按位右移 5 位
            hexLong = hexLong >> 5;

        }

        // 把字符串存入对应索引的输出数组
        return outChars.toString();

    }
}
