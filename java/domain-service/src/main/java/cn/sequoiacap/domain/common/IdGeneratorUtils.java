package cn.sequoiacap.domain.common;

import java.util.UUID;

/**
 * 8位短ID生成器
 * <br>
 * 将UUID的32位的16进制数，每4位转成62进制，这样的短ID不仅有UUID不重复的特性，还不占用空间，8位ID在一些查询等操作的性能上也优于32位ID。
 *
 * @author liuhao
 * @date 2021/12/10
 */
public class IdGeneratorUtils {

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    /**
     * 生成短的UUID
     *
     * @return 返回短ID
     */
    public static String getShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(chars[x % 0x3E]);
        }
        return shortBuffer.toString();

    }
}
