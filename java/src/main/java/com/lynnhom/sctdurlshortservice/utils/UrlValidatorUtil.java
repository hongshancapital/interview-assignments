package com.lynnhom.sctdurlshortservice.utils;

import org.apache.commons.validator.routines.UrlValidator;

/**
 * @description: URL校验工具
 * @author: Lynnhom
 * @create: 2021-10-28 15:55
 **/
public class UrlValidatorUtil {
    private UrlValidatorUtil(){}

    /**
     * 校验http链接的正确性
     * @param url
     * @return
     */
    public static boolean isValid(String url) {
        UrlValidator defaultValidator = new UrlValidator(new String[]{"http", "https"});
        return defaultValidator.isValid(url);
    }
}
