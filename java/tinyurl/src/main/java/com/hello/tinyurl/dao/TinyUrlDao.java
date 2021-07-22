package com.hello.tinyurl.dao;

import com.hello.tinyurl.util.ConvertUtil;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: Shuai
 * @date: 2021-7-5 21:56
 * @description:
 */
@Repository("tinyUrlDao")
public class TinyUrlDao {

    /**
     * key: tinyUrl,
     * value: originalUrl
     */
    private final Map<String, String> uniqueIdMap = new HashMap<>();
    /**
     * key: originalUrl,
     * value: tinyUrl
     */
    private final Map<String, String> originalUrlMap = new HashMap<>();

    @Resource
    private UniqueIdDao uniqueIdDao;

    public String saveOriginalUrl(String originalUrl) throws Exception {
        if (!StringUtils.hasText(originalUrl))
            throw new Exception("input url empty.");
        String tinyUrl = originalUrlMap.get(originalUrl);
        if (tinyUrl == null) {
            Long id = uniqueIdDao.getUniqueId();
            tinyUrl = ConvertUtil.num2String(id);
            uniqueIdMap.putIfAbsent(tinyUrl, originalUrl);
            originalUrlMap.putIfAbsent(originalUrl, tinyUrl);
            return tinyUrl;
        }
        return tinyUrl;
    }

    public String getOriginalUrl(String tinyUrl) throws Exception {
        if (tinyUrl == null)
            throw new Exception("input tinyUrl empty.");
        return uniqueIdMap.get(tinyUrl);
    }

}
