package pers.jenche.convertdomain.utilitie;

import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 *
 * @author jenche E-mail:jenchecn@outlook.com
 * @project convertdomain
 * @date 2021/11/15 14:29
 * @description 用来一般性的检查使用的方法都包含在此类；
 */
public class VerificationUtil {

    /**
     * 检查字符串是否为合法的URL
     *
     * @param uri {@link String}不为空的URL形式的字符串
     * @return {@link boolean}如果结果为true即合法
     */
    public static boolean validateURI(String uri) {
        //        匹配URL的正则，可以匹配URL中的大小写，例如百度搜索中的中文关键字URL转码的形式
        String pattern = "^((https|http|ftp|rtsp|mms)?://)" +
                "?(([0-9a-zA-Z_!~*'().&=+$%-]+: )?[0-9a-zA-Z_!~*'().&=+$%-]+@)?" + //ftp的user@ +
                "(([0-9]{1,3}\\.){3}[0-9]{1,3}" + // IP形式
                "|" + // 允许IP和DOMAIN（域名）
                "([0-9a-zA-Z_!~*'()-]+\\.)*" + // 域名- www.
                "([0-9a-zA-Z][0-9a-zA-Z-]{0,61})?[0-9a-zA-Z]\\." + // 二级域名匹配61位长度的二级域名
                "[a-zA-Z]{2,6})" + // 域名级别，例如com net等等最多可以匹配到6位
                "(:[0-9]{1,4})?" + // 端口
                "((/?)|(/[0-9a-zA-Z_!~*'().;?:@&=+$,%#-]+)+/?)$";

        Pattern httpPattern = Pattern.compile(pattern);
        return httpPattern.matcher(uri).matches();
    }
}
