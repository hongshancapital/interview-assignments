package com.ascmix.surl.utils;

public class KeyGenerator {
    private static final int BASE = 62;
    private static final char[] DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    private static long shuffle(long original) {
        return original;
    }

    private static String encrypt(long id) {
        long k = shuffle(id);
        StringBuilder builder = new StringBuilder();
        while (k > 0) {
            builder.insert(0, DIGITS[(int) (k % BASE)]);
            k /= BASE;
        }
        return builder.toString();
    }


    public static String gen() {
        long id = IdGenerator.getInstance().nextId();
        return encrypt(shuffle(id));
    }
}
