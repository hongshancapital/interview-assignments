import org.junit.platform.commons.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConsoleTest {
    public static void main(String[] args) {
        String url = "https://m.gmw.cn/2021-11/07/content_35292677.htm";
        System.out.println(isUrl(url));
    }
    public static boolean isUrl(String urls) {
        if(StringUtils.isBlank(urls)) {
            return  true;
        }
        String regex = "(ht|f)tp(s?)\\:\\/\\/[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*(:(0-9)*)*(\\/?)([a-zA-Z0-9\\-\\.\\?\\,\\'\\/\\\\&%\\+\\$#_=]*)?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(urls.trim());
        boolean result = matcher.matches();
        return result;
    }
}
