/**
 * this is a test project
 */

package com.example.interviewassgnments.entitys;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 计数器
 *
 * @Auther: maple
 * @Date: 2022/1/19 10:08
 * @Description: com.example.interviewassgnments.entitys
 * @version: 1.0
 */
@Data
public class DacService {

    // 计数器的类型
    private int counterType = 1;
    // 计数器数量限制
    private int counterThreshold = 100000;
    // 对象Key的访问计数器
    private Map<String, Integer> itemMap;

    /**
     * 无参构造函数
     */
    public DacService() {
        new DacService(counterType, counterThreshold);
    }

    /**
     * 计数器构造函数
     *
     * @param counterType      计数器类型
     * @param counterThreshold 计数器数量限制
     */
    public DacService(int counterType, int counterThreshold) {
        this.counterType = counterType;
        this.counterThreshold = counterThreshold;
        itemMap = new HashMap<>();
    }


    public boolean isItemFull(String itemKey) {
        boolean bRet = false;
        if (this.counterType == 1) {
            if (itemMap.containsKey(itemKey)) {
                synchronized (itemMap) {
                    Integer value = itemMap.get(itemKey);
                    if (value >= this.counterThreshold - 1) {
                        bRet = true;
                    }
                }
            }
        }
        return bRet;
    }

    public void resetItemKey(String itemKey) {
        if (this.counterType == 1) {
            if (itemMap.containsKey(itemKey)) {
                synchronized (itemMap) {
                    itemMap.put(itemKey, 0);
                }
            }
        }
    }

    /**
     * 更新对象Key的计数
     *
     * @param itemKey 对象Key
     */
    public void putItemKey(String itemKey) {
        if (this.counterType == 1) {
            synchronized (itemMap) {
                if (itemMap.containsKey(itemKey)) {
                    Integer value = itemMap.get(itemKey);
                    value++;
                    itemMap.put(itemKey, value);
                } else {
                    itemMap.put(itemKey, 1);
                }
            }
        }
    }

    /**
     * 获取Key的计数器值
     *
     * @param itemKey 计数器Key
     * @return int
     */
    public int getCounterNum(String itemKey) {
        Integer num = itemMap.get(itemKey);
        return num != null ? num : 0;
    }

    public int getCounterThreshold() {
        return counterThreshold;
    }
}
