package com.assignment.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * 短链摘要生成算法
 * 算法参考链接：https://segmentfault.com/a/1190000012088345
 * 1.将长网址md5生成32位签名串，分为4段，每段8个字节；
 * 2.对这四段循环处理，取8个字节，将他看成16进制串与0x3fffffff(30位1)与操作，即超过30位的忽略处理；
 * 3.这30位分成6段，每5位的数字作为字母表的索引取得特定字符，依次进行获得6位字符串；
 * 4.总的md5串可以获得4个6位串；取里面的任意一个就可作为这个长url的短url地址；
 *
 * @author mrdiyewu@gmail.com
 * @date 2021/10/11 15:33
 */
public class ConvertUtil {
    public static String ShortUrl(String url) {
        //混合KEY
        String key = "S5XpXQSkiH93HBBE";
        //生成URL的字符
        String[] chars = new String[]{
                "a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x",
                "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D",
                "E", "F", "G", "H", "I", "J", "K", "L",
                "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"
        };
        //对传入网址进行MD5加密
        String hex = DigestUtils.md5Hex(key + url);
        //得到 4组短链接字符串
        // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
        String sTempSubString = hex.substring(0, 8);
        long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
        StringBuilder outChars = new StringBuilder();
        //循环获得6位的字符串
        for (int j = 0; j < 6; j++) {
            // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引(具体需要看chars数组的长度   以防下标溢出，注意起点为0)
            long index = 0x0000003D & lHexLong;
            // 把取得的字符相加
            outChars.append(chars[(int) index]);
            // 按位右移 5 位
            lHexLong = lHexLong >> 5;
        }
        // 把字符串存入对应索引的输出数组
        return "https://goo.gl/" + outChars;
    }


    public static void main(String[] args) {
        System.out.println(ShortUrl("www.baidu.com"));
    }
}
