package com.zhangliang.suril.util;

import com.zhangliang.suril.configuration.CodeMessageConfiguration;
import io.netty.util.internal.StringUtil;
import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.util.StringUtils;

/**
 * 断言工具
 *
 * @author zhang
 * @date 2021/12/01
 */
public class AssertUtils {

    /**
     * 是否是网址
     *
     * @param value 传入的URL
     */
    public static void isUrl(String value) {
        if (StringUtil.isNullOrEmpty(value)) {
            throw new IllegalArgumentException(CodeMessageConfiguration.getMessage(90003));
        } else {
            try {
                new URL(value);
            } catch (MalformedURLException var2) {
                throw new IllegalArgumentException(CodeMessageConfiguration.getMessage(90003));
            }
        }
    }
}
