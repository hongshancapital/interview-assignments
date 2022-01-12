package interview.assignments.zhanggang.support;

public class Base62Util {
    private static final char[] ALPHABETS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();

    private Base62Util() {
    }

    public static String base10to62(final long base10) {
        long seed = base10;
        final StringBuilder base62 = new StringBuilder();
        while (seed > 0) {
            base62.append(ALPHABETS[(int) (seed % 62)]);
            seed = seed / 62;
        }
        return base62.reverse().toString();
    }

    public static long base62to10(final String base62) {
        long value = 0;
        for (int i = 0; i < base62.length(); i++) {
            if ('a' <= base62.charAt(i) && base62.charAt(i) <= 'z')
                value = value * 62 + base62.charAt(i) - 'a';
            if ('A' <= base62.charAt(i) && base62.charAt(i) <= 'Z')
                value = value * 62 + base62.charAt(i) - 'A' + 26;
            if ('0' <= base62.charAt(i) && base62.charAt(i) <= '9')
                value = value * 62 + base62.charAt(i) - '0' + 52;
        }
        return value;
    }
}
