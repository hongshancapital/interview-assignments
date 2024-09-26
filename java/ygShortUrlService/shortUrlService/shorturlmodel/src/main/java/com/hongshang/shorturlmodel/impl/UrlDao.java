package com.hongshang.shorturlmodel.impl;

import com.hongshang.common.BaseHolder;
import com.hongshang.shorturlmodel.api.IGetShortStrStrategy;
import com.hongshang.shorturlmodel.api.IUrlDao;
import com.hongshang.shorturlmodel.po.AddrUrlPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 地址存储类
 *
 * @author kobe
 * @date 2021/12/19
 */
@Repository
public class UrlDao implements IUrlDao {

    @Value("${long.url.maxcapacity}")
    private Long maxcapacity;

    /**
     * 存储超时时长，单位为天
     */
    @Value("${long.url.timeout}")
    private Long shortUrlTimeout;

    /**
     * 产生短地址的策略
     */
    @Value("${short.url.strStrategy}")
    private String strStrategy;

    /**
     * 存储地址的容器
     */
    private static Map<String, AddrUrlPo> map = new ConcurrentHashMap<>();

    @Override
    public String getByKey(String key) {
        return map.get(key)==null?null:map.get(key).getUrlStr();
    }

    /**
     *  保存存地址对
     *
     * @param key  长地址或短地址
     * @param value 应相的短地址或长地址
     * @return boolean 是否成功 true: 成功 ， false:失败
     */
    @Override
    public boolean putKeyValue(String key, String value) {
        if(map.containsKey(key)){
            return false;
        }
        AddrUrlPo addrUrlPo = new AddrUrlPo();
        addrUrlPo.setStartDate(new Date());
        addrUrlPo.setUrlStr(value);
        map.put(key,addrUrlPo);
        return true;
    }

    /**
     * 清除过期的数据
     */
    @Override
    public void removeDelayData(){
        Iterator<Map.Entry<String, AddrUrlPo>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Date d2 = new Date();
            Map.Entry<String, AddrUrlPo> entry = entries.next();
            Date d1 =  (Date)entry.getValue().getStartDate();
            if(diffDate(d2,d1)>shortUrlTimeout) {
                map.remove(entry.getKey());
            }
        }
    }

    /**
     * 获取短地址
     *
     * @return String
     */
    @Override
    public String getShortUrl() {
        IGetShortStrStrategy getShortStrStrategy = (IGetShortStrStrategy)BaseHolder.getBean(strStrategy);
        return getShortStrStrategy.getNextShortStr();
    }


    /**
     * d2-d1=几天
     *
     * @param d2 第二个日期
     * @param d1 第一个日期
     * @return int 第二个 比 第一个日期  过去了多少天
     */
    private long diffDate(Date d2,Date d1){
        //毫秒ms
        long diff = d2.getTime() - d1.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays;
    }


}
