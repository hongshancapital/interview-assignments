package url.converter.util;

/**
 * 10进制与62进制转换工具类
 *
 * @author Wang Siqi
 * @date 2021/12/31
 */
public class Base62Util {

    // TODO 位置乱序
    private static final String BASE62_CODE = "Cnb0z7aoNf3jy52VAYOlMDmRqKIwZJHQrtevLX4G6TdxSuUEhkc8WB9iPsF1gp";
    private static final String DEFAULT = String.valueOf(BASE62_CODE.charAt(0)); // 短域名末位的默认填充字符
    private static final int SHORT_MAX_LENGTH = 8; // 短域名最大长度
    private static final long MAX_ID = 3521614606208L; // 自增最大id=62^7

    /**
     * 编码: 10进制 -> 62进制
     */
    public static String encode(long b10) {
        if (b10 < 0) {
            throw new IllegalArgumentException("num must be nonnegative");
        }
        StringBuilder res = new StringBuilder(SHORT_MAX_LENGTH);
        while (b10 > 0) {
            res.append(BASE62_CODE.charAt((int) (b10 % 62)));
            b10 /= 62;
        }
        return res.reverse().toString();
    }

    /**
     * 解码: 62进制 -> 10进制
     */
    public static long decode(String b62) {
        for (char character : b62.toCharArray()) {
            if (!BASE62_CODE.contains(String.valueOf(character))) {
                throw new IllegalArgumentException("Invalid character(s) in string: " + character);
            }
        }
        long ret = 0;
        b62 = new StringBuffer(b62).reverse().toString();
        long count = 1;
        for (char character : b62.toCharArray()) {
            ret += BASE62_CODE.indexOf(character) * count;
            count *= 62;
        }
        return ret;
    }

    /**
     * 编码: 10进制 -> 62进制, 且末尾填充默认字符
     *
     * @param b10
     * @return
     */
    public static String encodeWithDefault(long b10) {
        return encode(b10).concat(DEFAULT);
    }
}
