package com.shorturl.util;

public class ShortUrlCreateUtil {

    public static String ShortText(String string) {
        String key = "SHORTURL";                 //自己定义生成MD5加密字符串前的混合KEY
        String[] chars = new String[]{          //要使用生成URL的字符
                "a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x",
                "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D",
                "E", "F", "G", "H", "I", "J", "K", "L",
                "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"
        };
        String time = String.valueOf(System.currentTimeMillis());
        String hex = EncriptUtil.md5(key + string + time);

        String outChars = "";
        String subHex = hex.substring(0, 8);
        long idx = Long.valueOf("3FFFFFFFFF", 16) & Long.valueOf(subHex, 16);

        for (int k = 0; k < 9; k++) {
            int index = (int) (Long.valueOf("0000003D", 16) & idx);
            outChars += chars[index];
            idx = idx >> 1;
        }

        return outChars;
    }

}
