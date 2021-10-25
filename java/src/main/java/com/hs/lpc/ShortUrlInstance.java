package com.hs.lpc;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rickiyang
 * @date 2021-10-19
 * @Desc 短链接主类
 */
public class ShortUrlInstance {

    private static final String KEY = "lpc123456";

    private static final String[] strs = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
            "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
            "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    public static List<String> shortUrl(String url) {
        String encryptResult = encodeByMD5(url, KEY);
        List<String> resList = new ArrayList<>();
        for(int i = 0; i < 32; i = i + 8){
            String str = encryptResult.substring(i, i + 8);
            long bitCal = bitCalWith30(str);
            String strRes = getResultForLen6(bitCal, strs);
            resList.add(strRes);
        }
        return resList;
    }

    /**
     * 对传入网址进行 MD5 加密(第一步)
     * @param url 待加密的url
     * @param key 加密key值
     * @return result 结果值
     */
    private static String encodeByMD5(String url,String key) {
        String encryptResult = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((key + url).getBytes());
            byte[] digest = md.digest();
            encryptResult = DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return encryptResult;
    }

    /**
     * 与 0x3FFFFFFF 进行位与运算（第二步）
     * @param strPart 8位字符串
     * @return long结果
     */
    private static long bitCalWith30(String strPart) {
        //16进制转换
        long temp = Long.parseLong(strPart, 16);
        //位与运算
        long result = temp & 0x3FFFFFFF;
        return result;
    }

    /**
     * 获得6位的字符串
     * @param bitCalResult 第二步的结果
     * @param dict
     * @return
     */
    private static String getResultForLen6(long bitCalResult, String[] dict) {
        StringBuffer sb = new StringBuffer();
        //循环获得每组6位的字符串
        for (int j = 0; j < 6; j++) {
            long index = bitCalResult & 0x0000003D;
            sb.append(dict[(int)index]);
            bitCalResult = bitCalResult >> 5;
        }
        return sb.toString();
    }

}
