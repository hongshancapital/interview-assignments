package com.scdt.demo.common;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class URLUtils {

    private static final String SALT = "scdt";

    private static final String[] CHARS = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"
    };

    private static final String REGEX = "^[a-z0-9A-Z]+$";

    private static final int SIZE = 4;

    private static final int LENGTH = 6;

    private URLUtils() {}

    private static final UrlValidator VALIDATOR;

    static {
        VALIDATOR = new UrlValidator();
    }

    public static boolean isValidUrl(String url) {
        return VALIDATOR.isValid(url);
    }

    public static boolean isValidShortUrl(String shortUrl) {
        if (shortUrl == null || shortUrl.length() != LENGTH) {
            return false;
        }
        return shortUrl.matches(REGEX);
    }

    public static String generateShortUrl(String url) {
        byte[] bytes = (SALT + url).getBytes(StandardCharsets.UTF_8);
        String hex = DigestUtils.md5DigestAsHex(bytes);;
        String[] resUrl = new String[SIZE];
        for (int i = 0; i < SIZE; i++) {
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            StringBuilder outChars = new StringBuilder();
            for (int j = 0; j < LENGTH; j++) {
                long index = 0x0000003D & lHexLong;
                outChars.append(CHARS[(int) index]);
                lHexLong = lHexLong >> 5;
            }
            resUrl[i] = outChars.toString();
        }
        Random random = new Random();
        int i = random.nextInt(SIZE);
        return resUrl[i];
    }
}
