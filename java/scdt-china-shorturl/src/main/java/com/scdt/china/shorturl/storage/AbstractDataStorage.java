package com.scdt.china.shorturl.storage;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: zhouchao
 * @Date: 2021/12/08 17:44
 * @Description:
 */
public abstract class AbstractDataStorage implements DataStorage{

    public static final ConcurrentHashMap<Integer, DataStorage> dataStorageMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init(){
        dataStorageMap.put(getDataStorageType(),this);
    }

    public abstract int getDataStorageType();

}
