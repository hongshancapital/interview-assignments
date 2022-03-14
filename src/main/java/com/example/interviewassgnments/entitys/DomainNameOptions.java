/**
 * this is a test project
 */

package com.example.interviewassgnments.entitys;

import com.example.interviewassgnments.utils.BusinessException;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据存储结构定义及操作函数的定义类
 *
 * @Auther: maple
 * @Date: 2022/1/19 9:49
 * @Description: com.example.interviewassgnments.entitys
 * @version: 1.0
 */
@Data
public class DomainNameOptions {
    private static final long serialVersionUID = 1L;
    // 定义数据存储结构
    private Map<String, Map<String, String>> map;

    public DomainNameOptions() {
        map = new HashMap<>();
    }

    /**
     * 判断主Key是否存在
     *
     * @param key String
     * @return boolean true:存在 false:不存在
     * */
    private boolean hasKey(String key) {
        return map.containsKey(key);
    }

    /**
     * 判断hKey是否存在
     *
     * @param key String
     * @param hKey String
     * @return boolean true:存在 false:不存在
     * */
    private boolean hasKey(String key, String hKey) {
        if (hasKey(key)) {
            return map.get(key).containsKey(hKey);
        }
        return false;
    }

    /**
     * 添加Domain信息
     *
     * @param key    String 域名
     * @param hKey   String 短域名编码
     * @param hValue String 完整URL
     * @return boolean
     * @throws BusinessException
     */
    public synchronized boolean add(String key, String hKey, String hValue) throws BusinessException {
        try {
            if (hasKey(key)) {
                String temp = map.get(key).get(hKey);
                if (temp == null) {
                    map.get(key).put(hKey, hValue);
                } else {
                    map.get(key).replace(hKey, hValue);
                }
            } else {
                HashMap<String, String> temp = new HashMap<>();
                temp.put(hKey, hValue);
                map.put(key, temp);
            }
            return true;
        } catch (Exception ex) {
            throw new BusinessException(9999, ex.getMessage());
        }
    }

    /**
     * 获取完整URL
     *
     * @param key  String 域名
     * @param hKey String 短域名编码
     * @return String
     */
    public String find(String key, String hKey) {
        if (hasKey(key)) {
            String hValue = map.get(key).get(hKey);
            return hValue;
        }
        return null;
    }


}
