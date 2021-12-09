package com.cyh.assignment.util;


import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;

@Component
public class DomainConvertUtil {

    // 打乱字符顺序，一定程度上提升短域名安全性
    private static final char[] CHAR_ARRAY = { '9', 'u', 'D', 's', 'a', '6', 'I', '2', 'G', 'z', 'j', 'M', 'C', 'R',
            'g', '8', 'P', 'X', 'c', '4', 'Z', 'H', 'F', 'h', 't', 'm', 'V', 'n', 'i', 'w', 'L', 'W', 'Y', 'e', 'K',
            'y', 'q', 'O', '7', 'b', 'l', 'p', 'Q', '5', 'B', 'E', 'S', '3', 'T', 'U', 'd', 'A', 'x', '0', 'J', 'r',
            'k', '1', 'f', 'v', 'N', 'o' };

    private static final int RADIX = 62;

    /**
     * @param index 编号
     * @return 短域名
     */
    public static String indexToShort(Long index) {
        StringBuilder sb = new StringBuilder();
        long temp = index;
        while (temp > 0) {
            sb.append(CHAR_ARRAY[(int) (temp % RADIX)]);
            temp /= RADIX;
        }
        return sb.reverse().toString();
    }

}
