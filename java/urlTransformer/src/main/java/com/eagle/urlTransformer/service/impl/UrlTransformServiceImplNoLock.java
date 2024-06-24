package com.eagle.urlTransformer.service.impl;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.eagle.urlTransformer.service.UrlTransformService;
import com.eagle.urlTransformer.util.Constants;
import com.eagle.urlTransformer.util.MD5Util;
import com.eagle.urlTransformer.util.UrlCache;
import com.eagle.urlTransformer.util.UrlUtils;

import lombok.extern.slf4j.Slf4j;


/** 
 * @ClassName: UrlTransformServiceImpl 
 * @Description: 长短域名转换服务实现-不使用显式代码同步
 * @author Eagle
 * @date 2022年1月18日 下午10:23:10 
 *  
 */
@Service(value = "urlTransformServiceImplNoLock")
@Slf4j
public class UrlTransformServiceImplNoLock implements UrlTransformService {
	
	private Random random =new Random();
	
	private static final Lock lock = new ReentrantLock();
	
	/*  
	 *  存储长链接
	 * @param longUrl
	 * @return 长链接对应的短链接
	 * @see com.eagle.urlTransformer.service.UrlTransformService#saveLongUrl(java.lang.String) 
	 */
	public String saveLongUrl(String longUrl) {
		String shortUrl = null;
		if(!StringUtils.hasLength(longUrl)) throw new RuntimeException("请勿输入空字符串");;
		if(!UrlUtils.isValidUrl(longUrl)) throw new RuntimeException("请输入正确的url字符串");
		boolean putLongUrlSuccess = UrlCache.putLongUrl(longUrl);//为避免高并发下对同一个长链接重复操作，一上来就将长链接存入缓存
		if(!putLongUrlSuccess) {//putLongUrlSuccess为false表示缓存中已经有longUrl了，只需从缓存中取出相应短链接即可
			String tmpShortUrl = null;
			while(tmpShortUrl == null) {
				//有可能为空，因为相同长链接的另一个并发线程还未执行完.因此循环取值
				tmpShortUrl = UrlCache.getShortUrlByLongUrl(longUrl); 
				try {
					TimeUnit.MILLISECONDS.sleep(10L);
				} catch (InterruptedException e) {
					log.error(e.getMessage());
				}
			}
			 return tmpShortUrl;
		}
		/**此时有可能两个map中因为淘汰策略 ，存的数据并非完全一致，即在long2ShortMap中已被淘汰的长链接，
		在ShortMap2long中其实存在,因此先从ShortMap2long找一遍 **/
		String resultShortUrl = UrlCache.isLongUrlExists(longUrl);
		if(StringUtils.hasLength(resultShortUrl)) return resultShortUrl;
		String[] shortStrArr = genShortUrl(longUrl);
//		if(shortStrArr == null || shortStrArr.length == 0)  throw new RuntimeException("内部错误，请重试"); 不会出现此种情况
		log.debug("shortStr.length="+shortStrArr.length);
		shortUrl = UrlCache.putUrlPair(shortStrArr, longUrl);
		
		if(!StringUtils.hasLength(shortUrl)) {//生成的shortUrl有重复
			log.warn(longUrl+"---->生成的短链接与内存中重复");
			//兜底方案：生成随机字符串
			StringBuilder builder = new StringBuilder("");
			int reGen = 0;
			while(reGen < Constants.MAX_REGEN) {
				reGen++;
				String randomStr = gen8bitRandomStr(builder);
				String randomStrKey = UrlCache.putUrlPair(new String[] {randomStr}, longUrl);
				if(randomStr.equals(randomStrKey)) {
					UrlCache.putLongUrl(longUrl, randomStr);//用生成的短链接替换掉空字符串
					return randomStr;
				}
			}
			UrlCache.removeLongUrl(longUrl);//未能成功生成短链接，将长链接从缓存中删掉
			return null;//兜底失败，返回为空
//			throw new RuntimeException("内部错误，请重试"); //或者兜底失败 抛出异常
		}else {
			UrlCache.putLongUrl(longUrl, shortUrl);//用生成的短链接替换掉空字符串
			return shortUrl;
		}
	}

	/*  
	 *  
	 * 根据短链接获取对应的长链接
	 * @param shortUrl
	 * @return 
	 * @see com.eagle.urlTransformer.service.UrlTransformService#getLongUrlByShortUrl(java.lang.String) 
	 */
	public String getLongUrlByShortUrl(String shortUrl) {
		if(!StringUtils.hasLength(shortUrl)) return null;
		return UrlCache.getLongUrlByShort(shortUrl);
	}
	
	
    /** 
     * @Title: genShortUrl 
     * @Description: 以MD5为基础生成短链接
     * @param @param longUrl
     * @param @return    设定文件 
     * @return String[]    返回类型 
     * @throws 
     */ 
    private  String[] genShortUrl(String longUrl) {
	     // 
	     String md5Str =  MD5Util.genMd5String(longUrl);
	     if(md5Str == null) throw new RuntimeException("出错啦");
		 log.debug("md5String="+md5Str);
		 String[] resUrl = new String[4];
		//分成 4组短链接字符串
		 for ( int i = 0; i < 4; i++) {
	      String md5SubStr = md5Str.substring(i * 8, i * 8 + 8);
	   // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
		  long lHexLong = 0x3FFFFFFF & Long.parseLong (md5SubStr, 16);
		  String outChars = "" ;
		  for ( int j = 0; j < 6; j++) {
			// 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引(具体需要看chars数组的长度   以防下标溢出，注意起点为0)
		     long index = 0x0000003D & lHexLong;
		     // 把取得的字符相加
		    outChars += Constants.alphabets[( int ) index];
		    // 每次循环按位右移 5 位
		    lHexLong = lHexLong >> 5;
		  }
		  // 把字符串存入对应索引的输出数组
		    resUrl[i] = outChars;
		 }
		  return resUrl;
	   }

    /** 
     * @Title: gen8bitRandomStr 
     * @Description: 随机取字符的方式生成8位字符串
     * @param @param builder
     * @param @return    设定文件 
     * @return String    返回类型 
     * @throws 
     */ 
    private String gen8bitRandomStr(StringBuilder builder) {
    	
        for(int i = 0 ; i < Constants.SHORT_URL_MAX__LENGTH ; i++){
            int index = random.nextInt(Constants.ALPHABETS_LENGTH);
            builder.append(Constants.alphabets[index]);
        }

    	String result = builder.toString();
    	builder.delete(0, builder.length());//清空以备下一次使用
    	return result;
    }
}
