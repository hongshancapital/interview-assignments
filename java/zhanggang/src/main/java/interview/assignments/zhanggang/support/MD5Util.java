package interview.assignments.zhanggang.support;

import lombok.experimental.UtilityClass;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@UtilityClass
public class MD5Util {
    private static final char[] HEX_CODE = "0123456789abcdef".toCharArray();
    private static final String MD5 = "MD5";

    public String md5(final String source) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance(MD5);
        md.update(source.getBytes());
        byte[] digest = md.digest();
        return bytesToHex(digest);
    }

    private String bytesToHex(byte[] digest) {
        StringBuilder r = new StringBuilder(digest.length * 2);
        for (byte b : digest) {
            r.append(HEX_CODE[(b >> 4) & 0xF]);
            r.append(HEX_CODE[(b & 0xF)]);
        }
        return r.toString();
    }
}
