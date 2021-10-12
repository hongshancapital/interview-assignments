package com.meihua.shorturl.util;


import org.springframework.beans.factory.annotation.Value;
/**
 * @author meihua
 * @version 1.0
 * @date 2021/10/12
 */
public class IDGeneratorUtils {

    public static final String [] STR_ARR=  new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
            "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
            "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"
    };

    public static String secret;

    @Value("${url.host.secret:www.meihua.com}")
    public  void setSecret(String secret) {
        IDGeneratorUtils.secret = secret;
    }

    /**
     * @description: 根据id 转为62进制字符串
     * @param id: 自增id
     * @return 62进制字符串
     */
    public static String getId(Integer id){

        StringBuilder stringBuilder = new StringBuilder();
        while (id>0){
            int index = id%62;
            id = id /62;
            stringBuilder.insert(0,STR_ARR[index]);
        }
       return stringBuilder.toString();
    }



    public static String shortUrl(String url) {
        String md5EncryptResult = Encrypt.md5(secret + url);
        String hex = md5EncryptResult;
            // 把加密字符按照8位一组16进制与0x3FFFFFFF进行位与运算
            String sTempSubString = hex.substring(0,8);
            // 这里需要使用 long 型来转换，因为 Inteter.parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            for (int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += STR_ARR[(int) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            return outChars;
    }


}
