package com.yujianfei.wraper;

import com.yujianfei.entity.DNLongShortDto;
import com.yujianfei.entity.LongDN;
import com.yujianfei.entity.ShortDN;
import com.yujianfei.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class MemoryAdapter implements DNLongShortRepo {

    public final static ConcurrentHashMap<String, DNLongShortDto> map=new ConcurrentHashMap<>(16);

    @Override
    public void save(DNLongShortDto entity) {
        map.putIfAbsent(entity.getShortDN().getPath(),entity);
        map.putIfAbsent(MD5Util.encodeByMD5(entity.getLongDN().getUrl()),entity);
        log.info("内存保存信息");

    }

    /**
     * 读取
     */
    @Override
    public DNLongShortDto get(ShortDN shortDN) {
        log.info("内存获取信息");
        return map.get(shortDN.getPath());
    }

    /**
     * MD5读取
     */
    @Override
    public DNLongShortDto getByMD5(LongDN longDN) {
        log.info("MD5内存获取信息");
        return map.get(MD5Util.encodeByMD5(longDN.getUrl()));
    }

}
