package com.scdt.util;

import com.google.common.base.Preconditions;

import java.io.ByteArrayOutputStream;

public class Base62Util {

    private static char[] encodes = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
            .toCharArray();

    private static byte[] decodes = new byte[256];

    static {
        for (int i = 0; i < encodes.length; i++) {
            decodes[encodes[i]] = (byte) i;
        }
    }


    public static String encodeBase62(String content) {
        Preconditions.checkNotNull(content, "内容不能为空");
        char[] bytes = content.toCharArray();
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        int pos = 0, val = 0;
        for (int i = 0; i < bytes.length; i++) {
            val = (val << 8) | (bytes[i] & 0xFF);
            pos += 8;
            while (pos > 5) {
                char c = encodes[val >> (pos -= 6)];
                sb.append(
                        c == 'i' ? "ia" :
                                c == '+' ? "ib" :
                                        c == '/' ? "ic" : String.valueOf(c)
                );
                val &= ((1 << pos) - 1);
            }
        }
        if (pos > 0) {
            char c = encodes[val << (6 - pos)];
            sb.append(
                    c == 'i' ? "ia" :
                            c == '+' ? "ib" :
                                    c == '/' ? "ic" : String.valueOf(c));
        }
        return sb.toString();
    }

    public static String decodeBase62(String content) {
        Preconditions.checkNotNull(content, "内容不能为空");
        char[] bytes = content.toCharArray();
        ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length);
        int pos = 0, val = 0;
        for (int i = 0; i < bytes.length; i++) {
            char c = bytes[i];
            if (c == 'i') {
                c = bytes[++i];
                c =
                        c == 'a' ? 'i' :
                                c == 'b' ? '+' :
                                        c == 'c' ? '/' : bytes[--i];
            }
            val = (val << 6) | decodes[c];
            pos += 6;
            while (pos > 7) {
                baos.write(val >> (pos -= 8));
                val &= ((1 << pos) - 1);
            }
        }
        return new String(baos.toByteArray());
    }

    public static void main(String[] args) {
        System.out.println(encodeBase62("111111"));
    }
}
