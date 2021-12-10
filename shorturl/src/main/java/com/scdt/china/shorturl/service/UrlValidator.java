package com.scdt.china.shorturl.service;

import java.util.regex.Pattern;

public class UrlValidator {

    private static final Pattern urlPattern = Pattern.compile("^\\w+[^\\s]+(\\.[^\\s]+){1,}$");

    public static boolean validate(String url) {
        return urlPattern.matcher(url).matches();
    }
}
