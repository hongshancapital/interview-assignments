package com.scdt.shortenurl.common.utils;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description 编码工具类
 * @Author chenlipeng
 * @Date 2022/3/7 2:14 下午
 */
public class EncodeUtils {

    static final char[] DIGITS =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                    'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
                    'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D',
                    'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N',
                    'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 将十进制数字编码为62进制
     * @param seq
     * @return
     */
    public static String encodeBy62Decimal(long seq) {
        StringBuilder sb = new StringBuilder();
        while (true) {
            int index = (int) (seq % 62);
            sb.append(DIGITS[index]);
            seq = seq / 62;
            if (seq == 0) {
                break;
            }
        }
        return sb.reverse().toString();
    }

    /**
     * MD5编码
     * @param input 需要编码的字符串
     * @param key 秘钥
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String encodeByMd5(String input, String key) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update((input + key).getBytes());
        byte[] digest = md.digest();
        return DatatypeConverter.printHexBinary(digest).toUpperCase();
    }

}
