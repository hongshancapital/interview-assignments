package com.wb.http_server.util;

import sun.misc.BASE64Decoder;

/**
 * @author bing.wang
 * @date 2021/1/15
 */
public class Base64 {
    public static String getFromBASE64(String s) {
        if (s == null)
            return null;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            byte[] b = decoder.decodeBuffer(s);
            return new String(b);
        } catch (Exception e) {
            return null;
        }
    }
}
