package com.example.utils;

/**
 * @projectName: shortUrl
 * @package: com.example.utils
 * @className: CMyKey
 * @description: 防止map反复存储同一Key出现内存溢出
 * @author: Kai
 * @version: v1.0
 */
public class CMyKey {
    private String key;

    public CMyKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CMyKey)
            return key.equals(((CMyKey) obj).key);
        else
            return false;
    }

    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
