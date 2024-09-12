package com;

import com.google.common.base.Strings;

import java.util.Optional;

public class TestData {

    public static final String LONG_URL = "http://www.baidu.com/";
    public static final String SHORT_URL = "http://t.cn/ABCDEF";
    public static final String EMPTY_STRING = "";
    public static final String SHORT_URL_ILLEGAL = "http://sina.cn/ABCDEF";
    public static final String SHORT_URL_ILLEGAL2 = "http://t.cn/ABC/DEF";
    public static final String TOO_LONG_URL = "http//www.baidu.com?key=" + Strings.repeat("A", 2000);

}
