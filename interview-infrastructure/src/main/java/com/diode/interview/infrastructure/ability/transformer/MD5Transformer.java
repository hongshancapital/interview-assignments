package com.diode.interview.infrastructure.ability.transformer;

import com.diode.interview.domain.exception.BizException;
import com.diode.interview.domain.ability.Transformer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author unlikeha@163.com
 * @date 2022/4/28
 */
@Slf4j
@Component
public class MD5Transformer implements Transformer {
    private static final String TYPE = "MD5";

    private static final String CUSTOM_KEY = "diode";

    //要使用生成URL的字符
    private static final String[] CHARS = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    @Override
    public String transform(String url) {
        String md5str = getMD5Str(url);
        log.debug("url:{} md5:{}", url, md5str);
        String sTempSubString = md5str.substring(8, 16);
        long lHexLong = 0x3FFFFFF3 & Long.parseLong(sTempSubString, 16);
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < 6; j++) {
            long index = 0x0000003C & lHexLong;
            sb.append(CHARS[(int) index]);
            // 每次循环按位右移 5 位
            lHexLong = lHexLong >> 5;
        }
        String res = sb.toString();
        log.debug("url:{} res:{}", url, res);
        return res;
    }

    private static String getMD5Str(String str) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update((CUSTOM_KEY + str).getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("getMD5Str error! str:{}", str);
            throw new BizException("短链接生成失败！");
        }
        byte[] byteArray = messageDigest.digest();
        StringBuilder md5StrBuff = new StringBuilder();
        for (byte b : byteArray) {
            if (Integer.toHexString(0xFF & b).length() == 1) {
                md5StrBuff.append("0").append(String.format("%02X", 0xFF & b));
            } else {
                md5StrBuff.append(String.format("%02X", 0xFF & b));
            }
        }
        return md5StrBuff.toString();
    }


    @Override
    public boolean suit(String type) {
        return TYPE.equals(type);
    }
}
