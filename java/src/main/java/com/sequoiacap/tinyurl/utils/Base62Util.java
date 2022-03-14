package com.sequoiacap.tinyurl.utils;

public final class Base62Util {

    private static final String BASE_CHARS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE_SIZE = BASE_CHARS.length();

    private Base62Util() {
    }

    public static boolean isValidBaseChar(char c) {
        for (int i = 0; i < BASE_SIZE; ++i) {
            if (BASE_CHARS.charAt(i) == c) {
                return true;
            }
        }
        return false;
    }

    public static String toBase(long id, int maxLen) {
        id = Math.abs(id);
        StringBuilder sb = new StringBuilder();
        while (id > 0) {
            sb.append(BASE_CHARS.charAt((int) (id % BASE_SIZE)));
            id = id / BASE_SIZE;
        }
        String baseCode = sb.reverse().toString();
        if (baseCode.length() > maxLen) {
            return baseCode.substring(baseCode.length() - maxLen);
        }
        return baseCode;
    }
}
