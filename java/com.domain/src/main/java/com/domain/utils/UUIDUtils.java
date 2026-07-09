/* Copyright (c) 2011-2013 GoPivotal, Inc. All Rights Reserved. Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and limitations under the
 * License. */
package com.domain.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.lang3.StringUtils;

/**
 * UUID工具类
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
public class UUIDUtils {

	private static boolean IS_THREADLOCALRANDOM_AVAILABLE = false;
	private static Random random;
	private static final long leastSigBits;
	private static final ReentrantLock lock = new ReentrantLock();
	private static long lastTime;
	private static String s_id="";

	static {
		try {
			IS_THREADLOCALRANDOM_AVAILABLE = null != UUIDUtils.class.getClassLoader().loadClass(
					"java.util.concurrent.ThreadLocalRandom");
		} catch (ClassNotFoundException e) {
		}

		byte[] seed = new SecureRandom().generateSeed(8);
		leastSigBits = new BigInteger(seed).longValue();
		if (!IS_THREADLOCALRANDOM_AVAILABLE) {
			random = new Random(leastSigBits);
		}
		int processID=getProcessID();
		String seqValue =StringUtils.leftPad(String.valueOf(processID),6,'0');
		if(StringUtils.isNoneBlank(seqValue)){
			s_id=seqValue;
		}

	}

	private UUIDUtils() {
	}
	public static final int getProcessID() {  
		 RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
		    String name = runtime.getName(); // format: "pid@hostname"
		    try {
		        return Integer.parseInt(name.substring(0, name.indexOf('@')));
		    } catch (Exception e) {
		        return 0;
		    }
    } 
	/**
	   * 随机生成UUID
	   * @return
	   */
	  public static synchronized String getUUID(){
	    UUID uuid=UUID.randomUUID();
	    String str = uuid.toString(); 
	    String uuidStr=str.replace("-", "");
	    return s_id+uuidStr;
	  }
	  /**
	   * 根据字符串生成固定UUID
	   * @param name
	   * @return
	   */
	  public static synchronized String getUUID(String name){
	    UUID uuid=UUID.nameUUIDFromBytes(name.getBytes());
	    String str = uuid.toString(); 
	    String uuidStr=str.replace("-", "");
	    return s_id+uuidStr;
	  }
	 
	public static void main(String[] args) {  
		System.out.println(UUIDUtils.getUUID());
		
		
	}
	/**
	 * Create a new random UUID.
	 *
	 * @return the new UUID
	 */
	public static String random() {
		byte[] randomBytes = new byte[16];
		/*if (IS_THREADLOCALRANDOM_AVAILABLE) {
			java.util.concurrent.ThreadLocalRandom.current().nextBytes(randomBytes);
		} else {
			random.nextBytes(randomBytes);
		}*/

		long mostSigBits = 0;
		for (int i = 0; i < 8; i++) {
			mostSigBits = (mostSigBits << 8) | (randomBytes[i] & 0xff);
		}
		long leastSigBits = 0;
		for (int i = 8; i < 16; i++) {
			leastSigBits = (leastSigBits << 8) | (randomBytes[i] & 0xff);
		}

		return new UUID(mostSigBits, leastSigBits).toString().replaceAll("-", "");
	}
	public static String generateString() {
		String allChar ="0123456789";
		String[] kdstart=new String[]{"268","368","58","3","1","2","6","8","V","10","12","19","7","6","5"};
		Map<String,Integer> map=new HashMap<String, Integer>();
		map.put("268",12);
		map.put("368",12);
		map.put("58",12);
		map.put("3",12);
		map.put("1",10);
		map.put("2",10);
		map.put("6",10);
		map.put("8",10);
		map.put("V",10);
		map.put("10",13);
		map.put("12",13);
		map.put("19",13);
		map.put("7",10);
		map.put("6",10);
		map.put("5",10);
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		int startkey=random.nextInt(kdstart.length);
		String start=kdstart[startkey];
		sb.append(start);
		int length=map.get(start);
		length=length-start.length();
		for (int i = 0; i < length; i++) {
		  sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}
	   return sb.toString();
	}
	/**
	 * Create a new time-based UUID.
	 *
	 * @return the new UUID
	 */
	public static String create() {
		long timeMillis = (System.currentTimeMillis() * 10000) + 0x01B21DD213814000L;

		lock.lock();
		try {
			if (timeMillis > lastTime) {
				lastTime = timeMillis;
			} else {
				timeMillis = ++lastTime;
			}
		} finally {
			lock.unlock();
		}

		// time low
		long mostSigBits = timeMillis << 32;

		// time mid
		mostSigBits |= (timeMillis & 0xFFFF00000000L) >> 16;

		// time hi and version
		mostSigBits |= 0x1000 | ((timeMillis >> 48) & 0x0FFF); // version 1

		return new UUID(mostSigBits, leastSigBits).toString().replaceAll("-", "");
	}

	 public static String getChar(int length) {
		 char[] ss = new char[length];
		int i=0;
		while(i<length) {
		    int f = (int) (Math.random()*3);
		    if(f==0)  
		     ss[i] = (char) ('A'+Math.random()*26);
		    else if(f==1)  
		     ss[i] = (char) ('a'+Math.random()*26);
		    else 
		     ss[i] = (char) ('0'+Math.random()*10);    
		    i++;
		 }
			String str=new String(ss);
		 return str;
	 }

}
