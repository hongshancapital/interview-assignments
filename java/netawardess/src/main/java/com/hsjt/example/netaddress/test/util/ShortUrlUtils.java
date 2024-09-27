package com.hsjt.example.netaddress.test.util;


import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.hsjt.example.netaddress.common.bean.UserKeyIdVO;
import com.hsjt.example.netaddress.common.bean.WebShotUrlVO;
import com.hsjt.example.netaddress.common.utils.Md5Utils;
import com.hsjt.example.netaddress.common.utils.SysUtils;
import com.hsjt.example.netaddress.common.utils.TimeUtils;

/**
 * 工具类
 * @author dazzling
 */
public class ShortUrlUtils {
    //生成 URL 的36个字符
   private static String[] CHARS = new String[]{
		   "A", "B", "C", "D", "E", "F", "G", "H","I", "J", "K", "L","M", 
		   "N", "O", "P", "Q", "R", "S", "T","U", "V", "W", "X", "Y", "Z",
		   "1", "2", "3", "4", "5","6", "7", "8", "9","0",
    };
 
	// 短网址前缀
   public static String SHORTURL = "SHORTURL:";
    // 长网址前缀
   public static String LONGURL = "LONGURL:";
   // 存储用户信息
   public static Map<String, UserKeyIdVO> userMap = new ConcurrentHashMap<String, UserKeyIdVO>();
   // 存储网址转换
   public static Map<String, WebShotUrlVO> urlMap = new ConcurrentHashMap<String, WebShotUrlVO>();
   
   static {
	   UserKeyIdVO userVo = new UserKeyIdVO();
	   userVo.setId(SysUtils.userId);
	   userVo.setKeyId(SysUtils.sysKey);
	   userVo.setKeyId2(SysUtils.sysKey+"dssfasd");
	   userMap.put(userVo.getId(), userVo);
	   WebShotUrlVO  longurl2 = new WebShotUrlVO(UUID.randomUUID().toString(),SysUtils.userId,SysUtils.sysKey,"chang","duan");
	   longurl2.setOverTime(TimeUtils.getDateTime(LocalDateTime.now().plusMinutes(-10)));
	   urlMap.put("cv",longurl2);
   }

	 
	 /**
	  * 生成几位短链接
	  * @param url 路径
	  * @param key 密钥
	  * @return
	  */
	 public static String shortUrl(String url,String key) {
	    // 对传入网址进行+key MD5 加密
	    String hex = Md5Utils.md5ByHex(key + url);
	    Integer subLen = hex.length() / SysUtils.sysShl;
	    String outChars = "";
	    int ywi = 32;
	     //  32 33 34 35 
	    for (int i = 0; i < SysUtils.sysShl; i++) {
	    	// 把加密字符 16 进制与 0x3FFFFFFF（30位1） 进行位与运算（作用：将生成的long型数据控制在30位）
	    	String sTempSubString = hex.substring(i * subLen, (i + 1) * subLen );
	    	// 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用
	        // long ，则会越界与 0x3FFFFFFF（30位1） 进行位与运算（作用：因为long是64位，将生成的long型数据控制在30位）
	        long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
	        // 把得到的值与 0x0000001f 进行位与运算（作用，0x0000001f是16进制，转化为10进制为31，
	        // 取得字符数组 chars 索引//
	        long index = 0x0000001f & lHexLong;
	       // 把取得的字符
	        String charutr = CHARS[(int) index];
	        // 判断是否有双重复 双重复往后移取后4位 最多移4位 
	        if(outChars.contains(charutr+charutr) && ywi< 35) {
	        	index = ywi;
	        	ywi++;
	        	// System.out.println("---重复---"+CHARS[(int) index]);
	        }
	        outChars += CHARS[(int) index];
	    }  
	        return SysUtils.sysDomain+outChars;
	 }

}
