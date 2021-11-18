package pers.jenche.convertdomain.utilitie;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 *
 * @author jenche E-mail:jenchecn@outlook.com
 * @project convertdomain
 * @date 2021/11/15 21:07
 * @description 一些用来匹配的工具类
 */

public class MatchesUtil {

    public static String getSingleKey(String strShortUri) {
        String key = "";
        String pattern = "\\.[a-zA-Z]{0,6}/[a-zA-Z0-9]{0,10}";
        Pattern httpPattern = Pattern.compile(pattern);
        Matcher matcher = httpPattern.matcher(strShortUri);
        if (matcher.find()){
            key =  matcher.group(0).split("/")[1];
        }
        return key;
    }
}
