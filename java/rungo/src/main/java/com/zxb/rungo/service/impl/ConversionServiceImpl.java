package com.zxb.rungo.service.impl;

import org.springframework.stereotype.Service;
import com.zxb.rungo.service.ConversionService;
import com.zxb.rungo.util.SnowflakeIdWorker;

@Service
public class ConversionServiceImpl implements ConversionService {
	private static String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static int scale = 62;
    
	@Override
	public String getShortAddress() {
		// TODO Auto-generated method stub
		SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(0, 0);
		long num = snowflakeIdWorker.nextId();
		
		StringBuilder sb = new StringBuilder();
        int remainder = 0;

        while (num > scale - 1) {
            /**
             * 对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转（reverse）字符串
             */
            remainder = Long.valueOf(num % scale).intValue();
            sb.append(chars.charAt(remainder));

            num = num / scale;
        }

        sb.append(chars.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        
        return value.substring(0, value.length() > 8? 8 : value.length());
	}

}
