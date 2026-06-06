package com.leo.store;

import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.leo.utils.ShortHash;

import lombok.extern.slf4j.Slf4j;

/**
 * 内存存储工具，如切换数据库，修改这里即可
 * @author LeoZhang
 *
 */
@Slf4j
public class MemStore {

	
	/**
	 * 主map， 存储 短连接 到 url的映射，不考虑hash碰撞问题
	 * 
	 * value使用软引用包装，再oom之前可以释放内存，防止因为cache过大而发生oom
	 */
	private static ConcurrentMap<String, SoftReference<String>> mainMap = new ConcurrentHashMap<>(1000*10000);

	 //ConcurrentMap<String, String> testMap = new MapMaker().initialCapacity(1000*10000).weakKeys().weakValues().makeMap();
	
	
	/**
	 * 扩展map，专门解决hash碰撞问题，key是一个7字符的短链接， value是一个列表<br>
	 *  列表中元素为原始url，当多个url发生hash碰撞时，将后续的url放到这个扩展map中<br>
	 *  列表长度为1个字符可以表示的最大个数，即62，超过62个碰撞后的url无法存储。
	 *  
	 *  用list存，写慢查快，这样更加符合短链系统的一般使用场景
	 */
	private static ConcurrentHashMap<String, List<String>> extendMap = new ConcurrentHashMap<>(10000);
	
	/**
	 * 将url存储下来，并获得最终的短url
	 * @param shortHash 根据算法获得短hash码，如果不发生冲突的话， 返回值与此参数一致
	 * @param url 原始url
	 * @return 当发生冲突时，在短hash码的基础上加一位，不发生冲突时，与短hash码一致
	 */
	public static String store(final String shortHash, final String url) {
		SoftReference<String> real = mainMap.putIfAbsent(shortHash, new SoftReference<String>(url));
		if (real == null|| url.equals(real.get())) {
			return shortHash;
		}
		//解决hash碰撞
		//从冲突url列表中查找，如果没有，记录进冲突列表
		extendMap.putIfAbsent(shortHash, new ArrayList<String>());
		List<String> list = extendMap.get(shortHash);
		int index = list.indexOf(url);
		if (index == -1) {//sync
			synchronized (list) { //list的写操作全部在同步块中完成，保证线程安全
				if (list.indexOf(url) == -1) {
					if (list.size() >= ShortHash.SHORT_URL_CHARSET_NUM) {
						//TODO 异常，考虑报警，这个冲突无法处理的情况值得分析
						log.error("shortUrl {} 对应的冲突url已经超过62个，该位置无法继续处理！放弃url {}" + url);
						return null;
					}
					list.add(url);
					index = list.size() -1;
				}
			}
		}
		
		return shortHash + ShortHash.SHORT_URL_CHARSET.charAt(index);
	}
	
	/**
	 * 根据短url查询原始url
	 * @param shortUrl
	 * @return
	 */
	public static String queryByShort(String shortUrl) {
		//七位和八位处理不同
		SoftReference<String> url = mainMap.getOrDefault(shortUrl, null);
		if (url != null) {
			if (url.get() == null) {
				//发生了gc，把软引用清理了一部分，执行检查，把value被清理的对应key删除
				 //checkAndCleanMainMap();
				 return null;
			} else {
				return url.get();//绝大多数这里就返回了
			}
		}
		if (shortUrl.length() == 8) {
			 List<String> list = extendMap.getOrDefault(shortUrl.substring(0, 7), null);
			if (list == null) {
				return null;
			}
			int index = ShortHash.SHORT_URL_CHARSET.indexOf(shortUrl.charAt(7));
			if (index >= list.size()) {
				return null;
			}
			return list.get(index);
		}
		return null;
	}
	
	/**
	 * 注意：这个方法只在主map守护进程中调用
	 * 
	 * 检查主map中的失效对象， 将对应的key删除掉
	 * 
	 * 此方法为同步方法，为了性能，不在put主map时同步
	 */
	public synchronized static void checkAndCleanMainMap() {
		
		Iterator<Entry<String, SoftReference<String>>> it = mainMap.entrySet().iterator();  
		 while(it.hasNext()){  
		     Map.Entry<String, SoftReference<String>> entry = it.next();  
		     if(entry.getValue() == null || entry.getValue().get() == null) {
		    	 it.remove();//使用迭代器的remove()方法删除元素  
		     }
		 }
	}

}
