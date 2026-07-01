package interview.assignments.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

/**
 * 短链接生成器
 * @author zhiran.wang
 * @date 2022/4/10 22:35
 */
@Slf4j
public class ShortUrlGenerator {

    private static String[] chars = new String[] {
            "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" ,
            "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,
            "u" , "v" , "w" , "x" , "y" , "z" , "0" , "1" , "2" , "3" , "4" , "5" ,
            "6" , "7" , "8" , "9" , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,
            "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,
            "U" , "V" , "W" , "X" , "Y" , "Z"
    };

    public static String generate(String longUrl){
        // 对传入网址进行MD5加密
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(longUrl.getBytes());
        String[] candidateUrls = new String[4];
        for ( int i = 0; i < 4; i++) {
            // 把加密字符按照8位一组16进制与0x3FFFFFFF进行位与运算
            String sTempSubString = md5DigestAsHex.substring(i * 8, i * 8 + 8);
            // 这里需要使用Long型来转换，因为Inteper.parseInt()只能处理31位，首位为符号位，如果不用Long则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong (sTempSubString, 16);
            String outChars = "" ;
            for ( int j = 0; j < 6; j++) {
                // 把得到的值与0x0000003D进行位与运算，取得字符数组chars索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += chars[( int ) index];
                // 每次循环按位右移5位
                lHexLong = lHexLong >> 5;
            }

            // 把字符串存入对应索引的输出数组
            candidateUrls[i] = outChars;
        }
        String shortUrl = candidateUrls[0];
        log.info("根据长链接{}生成短链接{}", longUrl, shortUrl);
        return shortUrl;
    }
}
