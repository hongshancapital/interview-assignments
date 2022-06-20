package com.heyenan.shorturldemo.untils;

import java.util.regex.Pattern;

/**
 * @author heyenan
 * @description 长域名校验
 *
 * @date 2020/5/07
 */
public class UrlCheck {

    /** 定义长域名输入规则 */
    private static final Pattern URL_REG = Pattern.compile("^(((ht|f)tps?):\\/\\/)?[\\w-]+(\\.[\\w-]+)+([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?$");

    /**
     * 长域名校验
     *
     * @param longUrl 长链接
     * @return 校验结果
     */
    public static boolean checkURL(String longUrl) {
        return URL_REG.matcher(longUrl).matches();
    }
}
