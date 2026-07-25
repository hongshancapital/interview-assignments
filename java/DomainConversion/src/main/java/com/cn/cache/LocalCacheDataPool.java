package com.cn.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wukui
 * @date 2021--12--29
 **/
//@Component
public class LocalCacheDataPool implements DataPool {
    private static final Logger log = LoggerFactory.getLogger(LocalCacheDataPool.class);
    //    public static Map<String,String> map = new HashMap<String,String>();

    private static int localMapMaxNum;
    private static Map<String, String> map = new LinkedHashMap<String, String>() {
        @Override
        protected boolean removeEldestEntry(Map.Entry<String, String> eldest){
            if (super.size()> localMapMaxNum){
//                log.error("本地数据存储空间已不足");
            }
            return size()> localMapMaxNum;
        }
    };

    @Override
    public String getData(String key) {
        return map.get(key);
    }

    @Override
    public void setData(String key, String value) {
        map.put(key, value);
    }

    @Override
    public void  clearData() {
        map.clear();
    }

    @Override
    public boolean deleteData(String key) {
        String temp = map.remove(key);
        if (StringUtils.hasText(temp))
            return true;
        else
            return false;
    }

    @Value("${domain.localMapMaxNum}")
    public  void setLocalMapMaxNum(int localMapMaxNum) {
        LocalCacheDataPool.localMapMaxNum = localMapMaxNum;
    }
}
