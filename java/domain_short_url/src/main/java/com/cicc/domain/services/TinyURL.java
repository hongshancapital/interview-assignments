package com.cicc.domain.services;

import java.security.MessageDigest;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Service;

import com.cicc.domain.DomainApplication;
import com.cicc.domain.jacoco.test.TinyURLTest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

/**
 * @author 施华海
 * @date 2021-04-17
 */
@Service
public class TinyURL {
	
    //保存域名映射数据
	private static Map<String, String> urlStore = new ConcurrentHashMap<String, String>();
    
	
	/** 
	* @Description 短域名存储接口: 接受长域名信息，返回短域名信息；保存短域名信息在JVM内存
	* @see 
	* @param  长域名信息 longUrl String Required=true
	* @return 返回短域名信息或空值 String 
	* @author 施华海
	* @throws 
	*/
	public String createTinyUrl(String longUrl,int len) {
		
		String tinyUrl = tinyUrlMD5(longUrl,len);
		urlStore.put(tinyUrl, longUrl);
		return tinyUrl;

	}

	/** 
	* @Description 短域名读取接口: 接受短域名信息，返回长域名信息；从JVM内存中获取长域名信
	* @see 
	* @param  短域名信息 tinyUrl String Required=true
	* @return 长域名信息或 'Not Found' String 
	* @author 施华海
	* @throws 
	*/
	public String getLongUrl(String tinyUrl) {
		return !StringUtils.isEmpty(tinyUrl) ? urlStore.get(tinyUrl):"Not Found";
	}
	public static void main(String[] args) {
		 String DEFAULT_LONG_URL = "https://www.jd.com/?cu=true&utm_source=baidu-pinzhuan&utm_medium=cpc&utm_campaign=t_288551095_baidupinzhuan&utm_term=0f3d30c8dba7459bb52f2eb5eba8ac7d_0_5c302666523f422fbbf1d2417a88e467";

		System.out.print(new TinyURL().tinyUrlMD5(DEFAULT_LONG_URL,8));
	
	}
	/** 
	* @Description 利用生成MD5加密字符传前的混合KEY的算法，将长域名转成短域名并返回
	* @see 
	* @param  长域名信息 url String Required=true
	* @return 短域名信息 String
	* @author 施华海
	* @throws 
	*/
	public static String tinyUrlMD5(String url,int len) {
		
		if( StringUtils.isEmpty(url) || len >8 )
		{
			return url;
		}
		// 可以自定义生成 MD5 加密字符传前的混合 KEY
		String key = "dGhpcyBpcyBhIGV4YW1wbGVhYQ==";
		// 要使用生成 URL 的字符
		String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
				"q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
				"W", "X", "Y", "Z"
		};
		// 对传入网址进行 MD5 加密
		String hex = md5ByHex(key + url);

		String[] resUrl = new String[4];
		for (int i = 0; i < 4; i++) {

			// 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
			String sTempSubString = hex.substring(i * 8, i * 8 + 8);

			// 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 ,
			// 如果不用long ，则会越界
			long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
			String outChars = "";
			for (int j = 0; j < len; j++) {
				// 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
				long index = 0x0000003D & lHexLong;
				// 把取得的字符相加
				outChars += chars[(int) index];
				// 每次循环按位右移 5 位
				lHexLong = lHexLong >> 5;
			}
			// 把字符串存入对应索引的输出数组
			resUrl[i] = outChars;
		}
		return resUrl[new Random().nextInt(4)];
	}
	
	/** 
	* @Description MD5加密(32位大写)
	* @see 
	* @param  任意非空字符串 src String Required=true
	* @return MD5值 String 
	* @author 施华海
	* @throws 
	*/
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
