package com.example.demo.utils;

import java.util.regex.Pattern;

/**
 * @ClassName ValidUrlUtils
 * @Description 校验链接是否合法
 * @Author gongguanghui
 * @Date 2021/11/26 11:38 AM
 * @Version 1.0
 **/
public class ValidUrlUtils {
    /**
     *（1）验证http,https,ftp开头
     *（2）验证一个":"，验证多个"/"
     *（3）验证网址为 xxx.xxx
     *（4）验证有0个或1个问号
     *（5）验证参数必须为xxx=xxx格式，且xxx=空    格式通过
     *（6）验证参数与符号&连续个数为0个或1个
     * @param url
     * @return true 合法 | false 非法
     */
    public static boolean validURL(String url) {
        boolean valid = true;
        String regex = "^([hH][tT]{2}[pP]:/*|[hH][tT]{2}[pP][sS]:/*|[fF][tT][pP]:/*)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+(\\?{0,1}(([A-Za-z0-9-~]+\\={0,1})([A-Za-z0-9-~]*)\\&{0,1})*)$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(url).matches()) {
            valid = false;
        }
        return valid;
    }
}
