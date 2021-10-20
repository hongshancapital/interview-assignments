package ligq.url.util;

/**
 * url摘要与int值互转工具类
 * @author ligq
 * @since 2021-10-18
 */
public class UrlDigest {
    //短url的基础字符
    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final long BASE = ALPHABET.length();

    public static String encode(int num) {
        long val = getUnsignedInt(num);
        StringBuilder sb = new StringBuilder();
        while (val > 0) {
            sb.append(ALPHABET.charAt((int) (val % BASE)));
            val /= BASE;
        }
        return sb.reverse().toString();
    }

    public static int decode(String str) {
        long val = 0;
        for (int i = 0; i < str.length(); i++ )
            val = val * BASE + ALPHABET.indexOf(str.charAt(i));
        return (int) val;
    }

    /** 将int值转换为无符号long值 */
    private static long getUnsignedInt(int val) {
        return val & 0x0FFFFFFFFl;
    }
}
