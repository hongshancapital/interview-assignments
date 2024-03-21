package cn.sequoiacap.links.base.utils;

import cn.sequoiacap.links.base.config.LinkConfig;

/**
 * @author : Liushide
 * @date :2022/4/5 19:18
 * @description : 链接处理工具类
 */
public class LinkUtil {
    /**
     * 可以自定义生成 MD5 加密字符传前的混合 KEY
     */
    private static final String KEY = "sequoia-cap" ;

    /**
     * char数组 62进制转换
     */
    private static final char[] CHARS = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private LinkUtil() { }

    /**
     * 将长链接转换为短链接
     * @param longUrl
     * @return
     */
    public static String generateShortCode(String longUrl) throws RuntimeException {
        // 对传入网址进行 MD5 加密
        String hex = MD5Util.md5Code(KEY + longUrl);

        // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算，固定取第三组
        String sTempSubString = hex.substring(2 * 8, 2 * 8 + 8);

        // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
        long lHexLong = 0x3FFFFFFF & Long.parseLong (sTempSubString, 16);
        //6位短码可以生成 568亿组合，7位短码可以生成 35216亿组合，这里为防止重复和以后拓展使用7位
        StringBuilder code = new StringBuilder(LinkConfig.DIGITS);
        for ( int j = 0; j < LinkConfig.DIGITS; j++) {
            // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
            long index = 0x0000003D & lHexLong;
            // 把取得的字符相加
            code.append(CHARS[(int)index]);
            // 每次循环按位右移 5 位
            lHexLong = lHexLong >> 5;
        }
        return code.toString();
    }

}
