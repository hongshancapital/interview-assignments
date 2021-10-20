package ligq.url.util;

import cn.hutool.core.lang.Assert;

import java.nio.charset.StandardCharsets;

/**
 * byte[]转化工具类
 * @author ligq
 * @since 2021-10-18
 */
public class BytesUtil {

    /**
     * int整数转换为4字节的byte数组
     * @param i  整数
     * @return byte数组
     */
    public static byte[] int2Bytes(int i) {
        byte[] targets = new byte[4];
        targets[3] = (byte) (i & 0xFF);
        targets[2] = (byte) (i >> 8 & 0xFF);
        targets[1] = (byte) (i >> 16 & 0xFF);
        targets[0] = (byte) (i >> 24 & 0xFF);
        return targets;
    }

    /**
     * byte数组转换为int整数
     * @param bytes byte数组
     * @return int整数
     */
    public static int bytes2Int(byte[] bytes) {
        Assert.isTrue(bytes.length == 4);
        int b0 = bytes[0] & 0xFF;
        int b1 = bytes[1] & 0xFF;
        int b2 = bytes[2] & 0xFF;
        int b3 = bytes[3] & 0xFF;
        return (b0 << 24) | (b1 << 16) | (b2 << 8) | b3;
    }

    /**
     * byte数组转换为String
     * @param bytes
     * @return
     */
    public static String bytes2Str(byte[] bytes) {
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * String转换为byte数组
     * @param str
     * @return
     */
    public static byte[] str2Bytes(String str) {
        return str.getBytes(StandardCharsets.UTF_8);
    }
}
