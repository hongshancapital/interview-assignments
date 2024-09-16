package com.service;

import com.utils.Constant;
import com.utils.ShortUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;


@Service
public class ShortUrlService {

    public String createUrl(String url) throws Exception {
        String shortUrl = null ;
        Map<String,String> item = Constant.URL_SHORTURL ;
        // 判断是否存URL
        if(item.containsKey(url)){
            shortUrl = item.get(url) ;
        }
        // 不存在创建短URL
        if (shortUrl == null) {
            try {
                String mdStr = null ;
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update((ShortUtil.key + url).getBytes());
                byte[] digest = md.digest();
                mdStr = DatatypeConverter.printHexBinary(digest).toUpperCase();

                String[] resUrl = new String[4];
                //得到 4组短链接字符串
                for (int i = 0; i < 4; i++) {
                    // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
                    String sTempSubString = mdStr.substring(i * 8, i * 8 + 8);
                    // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
                    long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
                    String outChars = "";
                    //循环获得每组6位的字符串
                    for (int j = 0; j < 6; j++) {
                        // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引(具体需要看chars数组的长度   以防下标溢出，注意起点为0)
                        long index = 0x0000003D & lHexLong;
                        // 把取得的字符相加
                        outChars += ShortUtil.chars[(int) index];
                        // 每次循环按位右移 5 位
                        lHexLong = lHexLong >> 5;
                    }
                    // 把字符串存入对应索引的输出数组
                    resUrl[i] = outChars;
                }

                shortUrl = getShortUrl(resUrl) ;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        item.put(url, shortUrl) ;
        return shortUrl;
    }

    private String getShortUrl(String[] resUrl){
        String str = StringUtils.join(resUrl, "") ;
        StringBuffer sb = new StringBuffer() ;
        Map<String,String> item = Constant.URL_SHORTURL ;
        System.out.println(str.getBytes()[(int)(Math.random()*24)]);
        for(int i=0; i < 8; i++){
            System.out.println(str.toCharArray()[(int)(Math.random()*24)]);
            sb.append(str.toCharArray()[(int)(Math.random()*24)]) ;
        }
        // 是否存在短连接
        if(item.containsValue(sb.toString())){
            return getShortUrl(resUrl);
        }
        return sb.toString() ;
    }

}
