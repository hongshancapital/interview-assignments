package com.zhangliang.suril.util;

import cn.hutool.core.util.StrUtil;
import com.zhangliang.suril.configuration.CodeMessageConfiguration;
import java.net.MalformedURLException;
import java.net.URL;

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
    public static void isUrl(CharSequence value) {
        if (StrUtil.isBlank(value)) {
            throw new IllegalArgumentException(CodeMessageConfiguration.getMessage(90003));
        } else {
            try {
                new URL(StrUtil.str(value));
            } catch (MalformedURLException var2) {
                throw new IllegalArgumentException(CodeMessageConfiguration.getMessage(90003));
            }
        }
    }
}
