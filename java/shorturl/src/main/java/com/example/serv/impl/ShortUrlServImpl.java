package com.example.serv.impl;

import com.example.serv.IShortUrlServ;
import com.example.utils.CMyEncrypt;
import org.springframework.stereotype.Service;

/**
 * @projectName: shortUrl
 * @package: com.example.serv.impl
 * @className: IShortUrlServ
 * @description: 短域名服务接口实例
 * @author: Kai
 * @version: v1.0
 */
@Service
public class ShortUrlServImpl implements IShortUrlServ {

    @Override
    public String transLong(String url) {
        String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
                "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z"
        };                                                                                                              // 要使用生成 URL 的字符
        String key = "key.com";                                                                                         // 可以自定义生成 MD5 加密字符传前的混合加密key
        String sMD5EncryptResult = CMyEncrypt.encodeByMD5(key + url);                                         // 对传入网址进行 MD5 加密，key是加密字符串
        String hex = sMD5EncryptResult;
        String sTempSubString = hex.substring(8, 16);                                                                   // 把加密字符按照8位一组16进制与0x3FFFFFFF进行位与运算
        long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);                                           // 这里需要使用 long 型来转换，因为 Inteter.parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
        String outChars = "";
        for (int j = 0; j < 8; j++) {
            long index = 0x0000003D & lHexLong;                                                                         // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
            outChars += chars[(int) index];                                                                             // 把取得的字符相加
            lHexLong = lHexLong >> 7;                                                                                   // 每次循环按位右移 7 位
        }

        return  outChars;
    }
}
