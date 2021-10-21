package com.chencaijie.domainservice.bean;

import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Map;

public class DomainStorage {
    private volatile static DomainStorage domainStorage;

    private DomainStorage() {
    }


    public static DomainStorage getDomainStorage() {
        if (null == domainStorage) {
            synchronized (DomainStorage.class) {
                if (null == domainStorage) {
                    domainStorage = new DomainStorage();
                }
            }
        }
        return domainStorage;
    }

    private Map<String,String> domainMap = new HashMap<>();

    public Map<String, String> getDomainMap() {
        return domainMap;
    }

    public void setDomainMap(Map<String, String> domainMap) {
        this.domainMap = domainMap;
    }
}
