package com.eagle.urlTransformer.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

import lombok.extern.slf4j.Slf4j;


/** 
 * @ClassName: MD5Util 
 * @Description: MD5工具类
 * @author Eagle
 * @date 2022年1月18日 下午6:29:16 
 *  
 */
@Slf4j
public class MD5Util {
	
    public static String genMd5String(String param) {
    	String result = null;
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update((param).getBytes());
			byte[] digest = md.digest();
			String sMD5EncryptResult = DatatypeConverter.printHexBinary(digest).toUpperCase();
			result = sMD5EncryptResult;
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage());
		}
		return result;
		
    }
    
}
