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
}
