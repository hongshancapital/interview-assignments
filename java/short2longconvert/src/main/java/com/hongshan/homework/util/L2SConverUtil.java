package com.hongshan.homework.util;

import org.springframework.util.DigestUtils;

public class L2SConverUtil {
    public static String[] convert(String longURL) {
        String rlt = null;
        //可以自定义生成MD5加密字符传前的混合KEY
        String key = "Heyx";
        //要使用生成URL的字符
        String[] chars = new String[]{
                "a","b","c","d","e","f","g","h",
                "i","j","k","l","m","n","o","p",
                "q","r","s","t","u","v","w","x",
                "y","z","0","1","2","3","4","5",
                "6","7","8","9","A","B","C","D",
                "E","F","G","H","I","J","K","L",
                "M","N","O","P","Q","R","S","T",
                "U","V","W","X","Y","Z"
        };

        //对传入网址进行MD5加密
        String hex = DigestUtils.md5DigestAsHex((key + longURL).getBytes() );
        String[] resUrl = new String[4];

        for (int i = 0; i < 4; i++)
        {
            //把加密字符按照8位一组16进制与0x3FFFFFFF进行位与运算
            String htmp= hex.substring(i*8,i*8+8);
            long hexint = 0x3FFFFFFF & Long.parseLong(htmp, 16);
            String outChars = new String();
            for (int j = 0; j < 6; j++)
            {
                //把得到的值与0x0000003D进行位与运算，取得字符数组chars索引
                int index = (int) (0x0000003D & hexint);
                //把取得的字符相加
                outChars += chars[index];
                //每次循环按位右移5位
                hexint = hexint >> 5;
            }
            //把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        return resUrl;

    }
}
