package cn.sequoiacap.links.base.utils;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author : Liushide
 * @date :2022/4/5 18:05
 * @description : MD5功能类
 */
@Slf4j
public final class MD5Util {

    /**
     * 私有构造方法避免 new 对象
     */
    private MD5Util () {
        // 不需要 public 构造方法
    }

    /**
     * MD5加码。32位
     * @param inStr
     * @return
     */
    public static String md5Code(String inStr) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            char[] charArray = inStr.toCharArray();
            byte[] byteArray = new byte[charArray.length];

            for (int i = 0; i < charArray.length; i++) {
                byteArray[i] = (byte) charArray[i];
            }

            byte[] md5Bytes = md5.digest(byteArray);

            StringBuilder hexValue = new StringBuilder();

            for (int i = 0; i < md5Bytes.length; i++) {
                int val = ((int) md5Bytes[i]) & 0xff;
                if (val < 16) { hexValue.append("0"); }
                hexValue.append(Integer.toHexString(val));
            }
            return hexValue.toString();
        } catch (NoSuchAlgorithmException e) {
            log.error("获取MD5 error! ", e);
            return null;
        }
    }

}
