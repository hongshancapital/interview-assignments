package com.d00216118.demo.util;

import org.springframework.util.DigestUtils;

import java.util.Date;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 4:02 下午 2021/3/31
 **/
public class VerifyCenter {

    public static boolean veriry(String sign, String username, String password, String timestamp){
        String temp=password+"|"+username+"|"+timestamp;
        //this is 32 bit md5
        String md5Sign=DigestUtils.md5DigestAsHex(temp.getBytes());
        if(md5Sign.compareToIgnoreCase(sign)==0)
            return true;
        else
            return false;
    }


//    public static void main(String[] args) {
//
//        String x="123|aabbcc";
//
//        String  md5Sign=DigestUtils.md5DigestAsHex(x.getBytes());
//
//        System.out.println(md5Sign);
//
//        System.out.println(md5Sign.compareToIgnoreCase("47F0A3632A93527E49A49B5BB745Af09"));
//
//    }


    public static void main(String[] args) {
        System.out.println(new Date().getTime());
    }

}
