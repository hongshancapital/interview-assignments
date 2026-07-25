package com.interview.wph.shorturl.common.utils;

import cn.hutool.bloomfilter.BitMapBloomFilter;

/**
 * @author wangpenghao
 * @date 2022/4/18 19:24
 */
public class BloomUtil {
    private static final BitMapBloomFilter filter = new BitMapBloomFilter(10);

    public static Boolean addId(Long id){
        return filter.add(id.toString());
    }

    public static Boolean containsId(Long id){
        return filter.contains(id.toString());
    }
}
