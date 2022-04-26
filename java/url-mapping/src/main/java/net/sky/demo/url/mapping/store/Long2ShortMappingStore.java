package net.sky.demo.url.mapping.store;

import org.apache.commons.collections4.trie.PatriciaTrie;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


/**
 * 基于前缀树的字符串到long的映射存储
 */
@Component
public class Long2ShortMappingStore {

    private PatriciaTrie<Long> patriciaTrie = new PatriciaTrie<Long>();


    /**
     * 查询字符串到long映射
     *
     * @param url 长域名字符串
     * @return 如果不存在，则返回null
     */
    public Long queryBySourceUrl(String url) {
        if (!StringUtils.hasText(url)) {
            throw new IllegalArgumentException("input url can not be null");
        }
        if (patriciaTrie.containsKey(url)) {
            return patriciaTrie.get(url);
        } else {
            return null;
        }
    }


    /**
     * 插入新的映射
     *
     * @param url   长域名字符串
     * @param value 数字
     */
    public void insertNewMapping(String url, Long value) {
        if (value == null || value.longValue() < 0) {
            throw new IllegalArgumentException("value can not null or negative");
        }
        if (!StringUtils.hasText(url)) {
            throw new IllegalArgumentException("input url can not be null");
        }
        patriciaTrie.put(url, value);
    }
}
