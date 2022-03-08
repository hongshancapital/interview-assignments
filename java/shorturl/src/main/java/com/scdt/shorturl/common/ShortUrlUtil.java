package com.scdt.shorturl.common;

import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ShortUrlUtil {
    public static final String[] CHARS = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };

    /**
     * 根据长链接地址，获取短链接数组
     *
     * @param longUrl 长链接地址
     * @param md5Key  md5加密混合key
     * @return 短链接数组，其中包含多个短链接，发生碰撞时可取数组中下一个短链接地址.
     */
    public static String generateShortUrl(String longUrl, String md5Key) {
        // 对传入长链接进行md5加密
        String md5EncryptResult = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((md5Key + longUrl).getBytes());
            byte[] digest = md.digest();
            md5EncryptResult = DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {//NOSONAR
            log.error(e.getMessage(), e);
        }

        List<String> resultUrls = new ArrayList<String>();
        // 获取4组短链接字符串结果
        for (int i = 0; i < 1; i++) {
            // 把加密字符按照8位一组16进制与0x3FFFFFFF进行按位与运算
            String sTempSubString = md5EncryptResult.substring(i * 8, i * 8 + 8);
            // 这里使用Long型来转换，因为Integer.parseInt()只能处理 31 位，首位为符号位，如果不用long，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            //循环获得每组6位的字符串
            for (int j = 0; j < 6; j++) {
                // 把得到的值与0x0000003D进行位与运算，取得字符数组chars索引(具体需要看chars数组的长度以防下标溢出，注意起点为0)
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += CHARS[(int) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            resultUrls.add(outChars);
        }
        return resultUrls.get(0);
    }
}
