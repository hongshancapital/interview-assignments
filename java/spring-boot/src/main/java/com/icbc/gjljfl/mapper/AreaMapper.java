package com.icbc.gjljfl.mapper;

import com.alibaba.fastjson.JSONObject;

import java.util.List;
import java.util.Map;

public interface AreaMapper {


    List<Map<String, Object>> queryProvinceList();

    List<Map<String, Object>> queryCityByProvinceCode(JSONObject jsonObject);

    List<Map<String, Object>> queryAreaByCityCode(JSONObject jsonObject);

    List<Map<String, Object>> queryCountyByArea(JSONObject jsonObject);

    List<Map<String, Object>> queryAreaByTownCode(JSONObject jsonObject);

    void addressUpdat(JSONObject jsonObject);

    String queryUserAddress(JSONObject jsonObject);

    String queryRecipientProvinceName(String recipientProvinceCode);

    String queryRecipientCityName(String recipientCityCode);

    String queryRecipientRegionName(String recipientCityCode);

    String queryRecipientTownName(String recipientTownCode);

    String queryRecipientShequName(String recipientShequCode);

    void updateUserShequId(JSONObject jsonObject);

    Map<String, String> selectAreaCodeFullInfo(Map<String, Object> map);
}
