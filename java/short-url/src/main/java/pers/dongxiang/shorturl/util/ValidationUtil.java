package pers.dongxiang.shorturl.util;

import cn.hutool.core.util.ReUtil;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.util.ObjectUtils;

/**
 * @ProjectName short-url
 * @Package pers.dongxiang.shorturl.util
 * @ClassName ValidationUtil
 * @Description 校验工具
 * @Company lab
 * @Author dongxiang
 * @Date 10/31/2021 11:30 PM
 * @UpdateUser
 * @UpdateDate
 * @UpdateRemark
 * @Version 1.0.0
 */
public class ValidationUtil {

    public static boolean isUrl(String url) {
        if(ObjectUtils.isEmpty(url)) {
            return false;
        }
        String regex = "(ht|f)tp(s?)\\:\\/\\/[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*(:(0-9)*)*(\\/?)([a-zA-Z0-9\\-\\.\\?\\,\\'\\/\\\\&%\\+\\$#_=]*)?";
        boolean result = ReUtil.isMatch(regex,url);
        return result;
    }

}
