package com.yujianfei.wraper;

import com.yujianfei.entity.DNLongShortDto;
import com.yujianfei.entity.LongDN;
import com.yujianfei.entity.ShortDN;

public interface DNLongShortRepo {

    /**
     * 存储短域名服务
     */
    void save(DNLongShortDto entity);

    /**
     * 通过MD5读取域名实体
     */
    DNLongShortDto getByMD5(LongDN longDN);

    /**
     * 通过短域名读取长域名
     */
    DNLongShortDto get(ShortDN shortDN);

}
