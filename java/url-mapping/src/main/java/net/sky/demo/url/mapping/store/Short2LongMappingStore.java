package net.sky.demo.url.mapping.store;

import net.sky.demo.url.mapping.util.Long2StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 基于数组存储短域名到长域名映射
 */
@Component
public class Short2LongMappingStore {

    //最大存储短域名数量
    @Value("${store.maxSize}")
    private Long maxStoreSize;
    @Value("${short.maxLength}")
    private Integer shortStringMaxLength;

    //短域名到长域名存储
    private List<List<String>> urlDataStore = new ArrayList<>();
    //单个List最大长度
    private int maxSingleListSize = Integer.MAX_VALUE;
    //list初始化长度
    private int defaultSingleListInitSize = 10000;


    /**
     * 基于数字查询长域名
     *
     * @param number 数字（短域名转换过来）
     * @return 长域名
     */
    public String getSourceUrlByNumber(Long number) {
        if (number == null || number.longValue() <= 0L) {
            throw new IllegalArgumentException("number can not be null and must be positive");
        }
        if (number > maxStoreSize) {
            throw new IllegalArgumentException("number bigger than maxStoreSize:" + maxStoreSize);
        }
        //no data
        if (urlDataStore.size() == 0) {
            return null;
        }
        long index = number - 1;
        int firstIndex = (int) (index / maxSingleListSize);
        int secondIndex = (int) (index % maxSingleListSize);
        if (urlDataStore.size() < firstIndex) {
            return null;
        }
        List<String> list = urlDataStore.get(firstIndex);
        if (list.size() < secondIndex) {
            return null;
        }
        return list.get(secondIndex);
    }


    /**
     * 生成长域名游标
     * 此处并不去重，由上层业务代码去重后再调用该方法
     *
     * @param url 长域名
     * @return 游标
     */
    public long generateNumber(String url) {
        if (!StringUtils.hasText(url)) {
            throw new IllegalArgumentException("input url can not be null");
        }
        long usedSize = getMaxNumber();
        if (usedSize > maxStoreSize) {
            throw new IllegalArgumentException("store has full");
        }
        //如果下一个游标映射完成长度超过8，则提示错误
        if (Long2StringUtil.long2String(usedSize + 1).length() > shortStringMaxLength) {
            throw new IllegalArgumentException("short reach max length:" + shortStringMaxLength);
        }
        List<String> list = getLastList();
        list.add(url);
        return usedSize + 1;
    }


    /**
     * 数组最大长度
     *
     * @return
     */
    private long getMaxNumber() {
        if (urlDataStore.isEmpty()) {
            return 0L;
        }
        return (urlDataStore.size() - 1) * 1L * maxSingleListSize + urlDataStore.get(urlDataStore.size() - 1).size();
    }


    /**
     * 获取当前可用List
     *
     * @return
     */
    private List<String> getLastList() {
        if (urlDataStore.isEmpty()) {
            urlDataStore.add(new ArrayList<>(defaultSingleListInitSize));
        }
        List<String> list = urlDataStore.get(urlDataStore.size() - 1);
        if (list.size() >= maxSingleListSize) {
            list = new ArrayList<>(defaultSingleListInitSize);
            urlDataStore.add(list);
        }
        return list;
    }


    public Long getMaxStoreSize() {
        return maxStoreSize;
    }

    public void setMaxStoreSize(Long maxStoreSize) {
        this.maxStoreSize = maxStoreSize;
    }

    public int getMaxSingleListSize() {
        return maxSingleListSize;
    }

    public void setMaxSingleListSize(int maxSingleListSize) {
        this.maxSingleListSize = maxSingleListSize;
    }

    public int getDefaultSingleListInitSize() {
        return defaultSingleListInitSize;
    }

    public void setDefaultSingleListInitSize(int defaultSingleListInitSize) {
        this.defaultSingleListInitSize = defaultSingleListInitSize;
    }

    public Integer getShortStringMaxLength() {
        return shortStringMaxLength;
    }

    public void setShortStringMaxLength(Integer shortStringMaxLength) {
        this.shortStringMaxLength = shortStringMaxLength;
    }
}
