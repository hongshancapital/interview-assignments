package pers.jenche.convertdomain.utilitie;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @author jenche E-mail:jenchecn@outlook.com
 * @project convertdomain
 * @date 2021/11/15 10:34
 * @description 哈希转码工具
 */
public class SHAEncryptUtil {


    /**
     * 为字符串SHA256加密
     * @param str{@link String} 需要加密的字符串
     * @return 加密后字符串
     */
    public static String getSHA256Str(String str) {
        return DigestUtils.sha256Hex(str);
    }
}
