package com.java.assignment.web.utils;

import org.apache.tomcat.util.buf.StringUtils;

import java.security.MessageDigest;
import java.util.*;

/**
 * 短连接生成工具
 *
 */
public class ShortUrlUtils {
    /**
     * 会使用到的常量
     */
    public static final long LONG_0X3FFFFFFF = 0x3FFFFFFF;
    public static final long LONG_0X0000003D = 0x0000003D;
    public static final int INDEX_404 = 404;
    public static final int LONG_0XFF = 0xFF;
    public static final int INDEX_0 = 0;
    public static final int INDEX_1 = 1;
    public static final int INDEX_4 = 4;
    public static final int INDEX_5 = 5;
    public static final int INDEX_6 = 6;
    public static final int INDEX_8 = 8;
    public static final int INDEX_16 = 16;
    public static final String SHORTURL = "SHORTURL_";

    private static Random random =new Random();

    public static void main(String[] args) {
        // 原始链接
        String sLongUrl = "https://mbd.baidu.com/newspage/data/landingsuper?context=%7B%22nid%22%3A%22news_9768954121710174625%22%7D&n_type=0";
        System.out.println("长链接:" + sLongUrl);

        // 将产生4组6位字符串
        String aResult = shortUrl(sLongUrl);
        // 打印出结果
        System.out.println("[]:" + aResult);

        // 生成4以内随机数
        int j = random.nextInt(4);
        // 随机取一个作为短链
        System.out.println("短链接:" + aResult);
    }

    public static String shortUrl(String url) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "jacygong";
        // 要使用生成 URL 的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
                "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z"
        };
        // 对传入网址进行 MD5 加密
        String hex = md5ByHex(key + url);

        List<String> resUrl = new ArrayList<>();
        for (int i = INDEX_0; i < INDEX_4; i++) {
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * INDEX_8, i * INDEX_8 + INDEX_8);
            // 这里需要使用 long 型来转换，因为 Integer.parseInt() 只能处理 31 位 , 首位为符号位 ,如果不用long，则会越界
            long lHexLong = LONG_0X3FFFFFFF & Long.parseLong(sTempSubString, INDEX_16);
            String outChars = "";
            for (int j = INDEX_0; j < INDEX_6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = LONG_0X0000003D & lHexLong;
                // 把取得的字符相加
                outChars += chars[(int) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> INDEX_5;
            }
            // 把字符串存入对应索引的输出数组
            resUrl.add(outChars);
        }
        return resUrl.get(random.nextInt(4));
    }


    /**
     * MD5加密(32位大写)
     *
     * @param src 原始字符串
     * @return 加密之后的字符串
     */
    private static String md5ByHex(String src) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = src.getBytes();
            md.reset();
            md.update(b);
            byte[] hash = md.digest();
            StringBuffer buffer = new StringBuffer();
            String stmp = "";
            for (int i = INDEX_0; i < hash.length; i++) {
                stmp = Integer.toHexString(hash[i] & LONG_0XFF);
                if (stmp.length() == INDEX_1) {
                    buffer.append("0" + stmp);
                } else {
                    buffer.append(stmp);
                }
            }
            return buffer.toString().toUpperCase();
        } catch (Exception e) {
            return "";
        }
    }
}