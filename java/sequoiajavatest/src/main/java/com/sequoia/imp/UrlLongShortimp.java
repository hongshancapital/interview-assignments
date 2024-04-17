package com.sequoia.imp;



import javax.xml.bind.DatatypeConverter;

import com.sequoia.api.UrlLongtoShortApi;
import com.sequoia.utill.Commstatic;
import com.sequoia.utill.Publicstatic;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UrlLongShortimp   implements  UrlLongtoShortApi{
	
  
    @Value("${short.url.prefix}")
    private String shortUrlPrefix;
    
    @Value("${long.url.prefix}")
    private String LongUrlPrefix;



    
    @Override
    public   String transLongtoShortUrl(String url) {
    	System.out.println("传入url="+url);
        Map<String, String> urlmap = Publicstatic.urlmap;
        if(urlmap==null) {
        	urlmap  =  new  HashMap<String, String>();
        }
        
        // 对传入网址进行 MD5 加密
        String sMD5EncryptResult = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(url.getBytes());
            byte[] digest = md.digest();
            sMD5EncryptResult = DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String   value =    resUrl(sMD5EncryptResult);
        String shortUrl = shortUrlPrefix + value;
        urlmap.put(value, url);    
        Publicstatic.setmap(urlmap);
        return shortUrl;
    }
    
    @Override
    public   String transShorttoLongUrl(String url) {
    	System.out.println("传入短域名="+url);
		if(url.length()<shortUrlPrefix.length()) {
			return  "短域名错误";
		}

    	String longUrl = Publicstatic.urlmap.get(url.substring(shortUrlPrefix.length()));
    	
    	if(longUrl==null) {
    		return  "请输入正确短域名";
    	}
    	   	
    	return longUrl;
    }
    
    
    public   String  resUrl(String  sMD5EncryptResult) {
    	String[] resUrl = new String[4];
    	 //得到 4组短链接字符串
        for (int i = 0; i < 4; i++) {
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = sMD5EncryptResult.substring(i * 8, i * 8 + 8);
            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            //循环获得每组6位的字符串
            for (int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引(具体需要看chars数组的长度   以防下标溢出，注意起点为0)
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += Commstatic.digits[(int) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        return resUrl[0];
    }

}
