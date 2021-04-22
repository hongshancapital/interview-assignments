package com.capital.domain.service.impl;

import com.capital.domain.service.IConversionService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author jiangts
 * @Classname ConversionApplication
 * @Description 域名长短转换
 * @Date 2021/4/19
 * @Version V1.0
 */

@Service
public class ConversionServiceImpl implements IConversionService {
    private AtomicLong sequence = new AtomicLong(0);
    static final char[] DIGITS =
            { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                    'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                    'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                    'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    private static Map<String, String> map = new HashMap<>();

    @Override
    public String langToShort(String longDomain) {
        //自增的long值
        long seq = sequence.incrementAndGet();
        StringBuilder sBuilder = new StringBuilder();
        //限制8位
        for(int i = 0; i <8; i++){
            int remainder = (int) (seq % 62);
            sBuilder.append(DIGITS[remainder]);
            seq = seq / 62;
            if (seq == 0) {
                break;
            }
        }
        //存储长短域名map
        map.put(sBuilder.toString(), longDomain);
        return sBuilder.toString();
    }


    @Override
    public String getLang(String shortDomain) {
        return map.get(shortDomain);
    }
}
