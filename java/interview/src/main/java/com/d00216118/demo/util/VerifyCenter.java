package com.d00216118.demo.util;

import com.d00216118.demo.dto.TinyUrlRequestDTO;
import com.d00216118.demo.dto.UrlRequestDTO;
import com.d00216118.demo.dto.UserRequestDTO;
import org.springframework.util.DigestUtils;

import java.net.URL;
import java.util.Date;

/**
 *  the veriry
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 4:02 下午 2021/3/31
 **/
public class VerifyCenter {

    /**
     * sign order is [ password+"|"+username+"|"+timestamp ]
     */
    public static boolean verifyUserRequestDTO(UserRequestDTO userRequestDTO){
        String temp=userRequestDTO.getPassword()+"|"+userRequestDTO.getUsername()+"|"+userRequestDTO.getTimestamp();
        //this is 32 bit md5
        String md5Sign=DigestUtils.md5DigestAsHex(temp.getBytes());
        if(md5Sign.compareToIgnoreCase(userRequestDTO.getSign())==0)
            return true;
        else
            return false;
    }

    public static boolean verifyUrlRequestDTO(UrlRequestDTO urlRequestDTO){
        String temp= urlRequestDTO.getUrl()+"|"+urlRequestDTO.getToken()+"|"+urlRequestDTO.getUsername()+"|"+urlRequestDTO.getTimestamp();
        String md5Sign=DigestUtils.md5DigestAsHex(temp.getBytes());
        System.out.println(md5Sign);
        if(md5Sign.compareToIgnoreCase(urlRequestDTO.getSign())==0)
            return true;
        else
            return false;
    }

    public static boolean verifyTinyUrlRequestDTO(TinyUrlRequestDTO tinyUrlRequestDTO){
        String temp= tinyUrlRequestDTO.getTinyUrl()+"|"+tinyUrlRequestDTO.getToken()+"|"+tinyUrlRequestDTO.getUsername()+"|"+tinyUrlRequestDTO.getTimestamp();
        String md5Sign=DigestUtils.md5DigestAsHex(temp.getBytes());
        System.out.println(md5Sign);
        if(md5Sign.compareToIgnoreCase(tinyUrlRequestDTO.getSign())==0)
            return true;
        else
            return false;
    }

    public static boolean veriryUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
