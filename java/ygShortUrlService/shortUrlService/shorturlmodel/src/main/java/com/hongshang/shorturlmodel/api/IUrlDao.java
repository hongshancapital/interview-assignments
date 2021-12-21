package com.hongshang.shorturlmodel.api;

/**
 * @author: yangguowen
 * @createDate: 2021/12/16
 * @description: 用于从存储介质 获取 地址，或保护 地址
 */
public interface IUrlDao {

    /**
     * 获取相关的地址值
     *
     * @param key 长地址或短地址
     * @return 应相的短地址或长地址
     */
    public String getByKey(String key);

    /**
     *  保存存地址对
     *
     * @param key  长地址或短地址
     * @param value 应相的短地址或长地址
     * @return boolean 是否成功 true: 成功 ， false:失败
     */
    public boolean putKeyValue(String key,String value);


    /**
     * 清除过期的数据
     */
    public void removeDelayData();

    /**
     * 获取一个8位字符的短地址
     * @return
     */
    public String getShortUrl();
}
