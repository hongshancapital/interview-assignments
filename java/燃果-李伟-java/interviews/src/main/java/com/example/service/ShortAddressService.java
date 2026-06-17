package com.example.service;

import com.example.bean.result.ResultRpc;

public interface ShortAddressService {

    /**
     * 根据场地址生成短地址，采用MD5方式进行生成
     * @param longAddress
     * @return
     */
    ResultRpc<String> genShortAddress(String longAddress);

    /**
     * 根据短地址获取长地址信息
     * @param shortAddress
     * @return
     */
    ResultRpc<String> getLongAddressByShortAddress(String shortAddress);
}
