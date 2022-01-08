package com.cn.cache;

/**
 * @author wukui
 * @date 2021--12--29
 **/
public interface DataPool {

    public String getData(String key);
    public void setData(String key, String value);
    public void clearData();
    public boolean deleteData(String key);
}
