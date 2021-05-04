package com.sequoiacap.sdn.service.impl;

import com.sequoiacap.sdn.service.DomainService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author : fanzhaofei
 * @date : 2021/5/4 21:30
 * @description: 转换服务实现类
 */
@Service
public class DomainServiceImpl implements DomainService {

    private final AtomicLong sequence = new AtomicLong(0);

    static final char[] DIGITS =
            { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
                    'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                    'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                    'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    private static final Map<String, String> CACHE_MAP = new HashMap<>();

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
        CACHE_MAP.put(sBuilder.toString(), longDomain);
        return sBuilder.toString();
    }

    @Override
    public String getLang(String shortDomain) {
        return CACHE_MAP.get(shortDomain);
    }
}
