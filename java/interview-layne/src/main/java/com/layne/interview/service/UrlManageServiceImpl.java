package com.layne.interview.service;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.layne.interview.model.ErrorCodeEnum;
import com.layne.interview.model.ManageException;
import com.layne.interview.util.DigestUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.metrics.buffering.BufferingApplicationStartup;
import org.springframework.context.ApplicationStartupAware;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * URL管理接口
 *
 * @author layne
 * @version UrlManage.java, v 0.1 2022/1/16 22:10 下午
 */
@Service
public class UrlManageServiceImpl implements UrlManageService {

    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlManageServiceImpl.class);

    /**
     * 内存中存放域名映射关系
     */
    private static final BiMap<String, String> urlMap = HashBiMap.create();

    /**
     * 自增序列号
     */
    private final static AtomicLong sequence = new AtomicLong(0);

    /**
     * 序列号间隔
     */
    private static final int SEQUENCE_SPLIT = 1;


    @Override
    public String storeUrl(String urlString) {

        // 如果已经存在此长域名直接返回
        if (urlMap.containsValue(urlString)) {
            return urlMap.inverse().get(urlString);
        }

        String shortUrl;

        // 将长url进行缩短，获取短url
        shortUrl = DigestUtil.encodeHex(urlString);


        // 兜底策略：判断生成的短域名是否存在，已存在则直接用唯一序列号
        if (urlMap.containsKey(shortUrl)) {
            shortUrl = String.valueOf(sequence.addAndGet(SEQUENCE_SPLIT));
        }

        // 放入map
        urlMap.put(shortUrl, urlString);

        LOGGER.info("长域名存储完成，longUrl = {} , shortUrl = {}", urlString, shortUrl);

        return shortUrl;
    }

    @Override
    public String getUrl(String shortUrl) {
        // 查询
        String longUrl = urlMap.get(shortUrl);

        // 判断查询结果
        if (StringUtils.isBlank(longUrl)) {
            LOGGER.error("查询请求输入的短域名无对应长域名数据, shortUrl = {}", shortUrl);
            throw new ManageException(ErrorCodeEnum.REQUEST_DATA_NOT_EXIST, "系统中无对应此短域名的长域名数据");
        }

        return longUrl;
    }
}
