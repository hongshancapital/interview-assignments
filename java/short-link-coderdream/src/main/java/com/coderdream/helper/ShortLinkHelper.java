package com.coderdream.helper;

import com.coderdream.utils.CommonUtil;
import com.coderdream.utils.Constants;
import org.springframework.stereotype.Component;

/**
 * 短链接辅助类
 *
 * @author CoderDream
 * @version 1.0
 * @date 2022/5/8
 */
@Component
public class ShortLinkHelper {

    /**
     * 生成短链码
     *
     * @param param 长链接字符串
     * @return 短链接
     */
    public String createShortLinkCode(String param) {
        long murmurhash = CommonUtil.murmurHash32(param);
        //进制转换
        return encodeToBase62(murmurhash);
    }

    /**
     * 10进制转62进制
     *
     * @param num 10进制数
     * @return 62进制字符串
     */
    private String encodeToBase62(long num) {
        // StringBuffer线程安全，StringBuilder线程不安全
        StringBuffer stringBuffer = new StringBuffer();
        do {
            int i = (int) (num % 62);
            stringBuffer.append(Constants.CHARS.charAt(i));
            num = num / 62;
        } while (num > 0);
        return stringBuffer.reverse().toString();
    }
}
