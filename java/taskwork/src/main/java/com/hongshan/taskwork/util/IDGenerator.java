package com.hongshan.taskwork.util;

import java.security.MessageDigest;

public class IDGenerator {  
  
    /** 
      * @param args 
      */  
    public static void main(String[] args) {  
   
       String sLongUrl = "https://www.jb51.net/softjc/410285.html" ; 
       String[] aResult = shortUrl (sLongUrl);  
       
       String aString = "";
       for ( int i = 0; i < aResult. length ; i++) {  
           System. out .println( "[" + i + "]:::" + aResult[i]);  
           aString += aResult[i];
       }  
       
       System.out.println(aString);
    }  
    
    public static String generate(String url) {
    	String[] aResult = shortUrl (url);  
        
        String aString = "";
        for ( int i = 0; i < aResult. length ; i++) {  
            System. out .println( "[" + i + "]:::" + aResult[i]);  
            aString += aResult[i];
        }  
    	return aString;
    }
   
    public static String[] shortUrl(String url) {  
       String key = "mengdelong" ;  
       String[] chars = new String[] { "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" ,  
              "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,  
              "u" , "v" , "w" , "x" , "y" , "z" , "0" , "1" , "2" , "3" , "4" , "5" ,  
              "6" , "7" , "8" , "9" , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,  
              "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,  
              "U" , "V" , "W" , "X" , "Y" , "Z"  
   
       };  
       String sMD5EncryptResult = md5ByHex(key + url);  
       String hex = sMD5EncryptResult;  
   
       String[] resUrl = new String[4];  
       for ( int i = 0; i < 4; i++) {  
   
           String sTempSubString = hex.substring(i * 8, i * 8 + 8);  
   
           long lHexLong = 0x3FFFFFFF & Long.parseLong (sTempSubString, 16);  
           String outChars = "" ;  
           for ( int j = 0; j < 6; j++) {  
              long index = 0x0000003D & lHexLong;  
              outChars += chars[( int ) index];  
              lHexLong = lHexLong >> 5;  
           }  
           resUrl[i] = outChars;  
       }  
       return resUrl;  
    }  
    
    public static String md5ByHex(String src) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = src.getBytes();
            md.reset();
            md.update(b);
            byte[] hash = md.digest();
            String hs = "";
            String stmp = "";
            for (int i = 0; i < hash.length; i++) {
                stmp = Integer.toHexString(hash[i] & 0xFF);
                if (stmp.length() == 1)
                    hs = hs + "0" + stmp;
                else {
                    hs = hs + stmp;
                }
            }
            return hs.toUpperCase();
        } catch (Exception e) {
            return "";
        }
    }
}