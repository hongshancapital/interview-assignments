package com.interview.util;

import com.interview.exception.BizException;
import com.interview.exception.ExceptionCodeEnum;

/**
 * 
 * @类名称   CompressTools.java
 * @类描述   <pre>压缩工具类</pre>
 * @作者     zhangsheng
 * @创建时间 2021年3月21日下午3:20:39
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本           修改人 		    修改日期 		           修改内容描述
 *     ------------------------------------------------------
 *     1.00 	 TODO 	2021年3月21日下午3:20:39           新建          
 *     ------------------------------------------------------
 * </pre>
 */
public class CompressTools {
	
	final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z', '-', '_'};
	
	/**
	 * 
	 * @方法名称 compressNumber
	 * @功能描述 <pre>对数字进行压缩转换，结果不超过8位</pre>
	 * @作者     zhangsheng
	 * @创建时间 2021年3月21日 下午3:07:12
	 * @param number
	 * @return
	 * @return
	 */
	public static String compressNumber(long number) throws BizException {
        char[] buf = {'0','0','0','0','0','0','0','0'};
        int charPos = 8;
        int radix = 1 << 6;
        long mask = radix - 1;
        do {
        	if(charPos == 0) {
        		throw new BizException(ExceptionCodeEnum.COMPRESS_NUM_TOO_LONG.getCode(), ExceptionCodeEnum.COMPRESS_NUM_TOO_LONG.getMessage());
        	}
            buf[--charPos] = digits[(int) (number & mask)];
            number >>>= 6;
        } while (number != 0);
        return new String(buf);
    }
	
	public static void main(String[] args) throws Exception {
		System.out.println(compressNumber(281474976710000L));
		//System.out.println(compressNumber(154));
	}

}
