package com.example.sequoiahomework.service.impl;

import com.example.sequoiahomework.common.ex.MaximumMemoryException;
import com.example.sequoiahomework.common.utils.JvmUtils;
import com.example.sequoiahomework.common.utils.UrlUtils;
import com.example.sequoiahomework.config.Container;
import com.example.sequoiahomework.service.ChangeUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;

/**
 * @author Irvin
 * @description 改变url的接口实现
 * @date 2021/10/9 19:46
 */
@Service
@Slf4j
public class ChangeUrlServiceImpl implements ChangeUrlService {

    @Resource
    private Container container;

    /**
     * 长域名转短域名
     *
     * @param oriUrl 接口入参，长域名
     * @return java.lang.String
     * @author Irvin
     * @date 2021/10/9
     */
    @Override
    public String longToShort(String oriUrl) {
        //url编码
        return Optional.ofNullable(decodeUrl(oriUrl))
                .map(this::getShort)
                .orElse(null);
    }


    /**
     * 根据短域名获得长域名
     *
     * @param oriUrl 接口入参，短域名
     * @return java.lang.String
     * @author Irvin
     * @date 2021/10/9
     */
    @Override
    public String shortToLong(String oriUrl) {
        //url编码
        return Optional.ofNullable(decodeUrl(oriUrl))
                //获取长链接
                .map(this::getLong)
                .orElse(null);
    }


    /**
     * 获取短链接
     *
     * @param url 解码后的长链接
     * @return java.lang.String
     * @author Irvin
     * @date 2021/10/9
     */
    private String getShort(String url) {
        //先查询该长链接在容器中是否存在
        if (!container.longKey.containsKey(url)) {
            //验证是否内存已满
            try {
                verifyMemory();
            } catch (MaximumMemoryException e) {
                log.error("内存上限异常", e);
                return null;
            }
            //创建短链接并塞入容器
            String shortUrl = createShortUrl(url);
            container.longKey.put(url, shortUrl);
            container.shortKey.put(shortUrl, url);
            return shortUrl;
        } else {
            //返回
            return container.longKey.get(url);
        }
    }


    private void verifyMemory() throws MaximumMemoryException{
        if (JvmUtils.getUsableByte() < JvmUtils.MIN_USABLE) {
            throw new MaximumMemoryException("可用内存已到达上限");
        }
    }

    /**
     * 创建一个不重复的短链接
     *
     * @param url 解码后的长链接
     * @return java.lang.String
     * @author Irvin
     * @date 2021/10/9
     */
    private String createShortUrl(String url) {
        String shortUrl;
        while (true) {
            String tempShort = UrlUtils.longToShort(url);
            if (!container.shortKey.containsKey(tempShort)) {
                shortUrl = tempShort;
                break;
            }
        }
        return shortUrl;
    }

    /**
     * url解码
     *
     * @param oriUrl url
     * @return java.lang.String
     * @author Irvin
     * @date 2021/10/9
     */
    private String decodeUrl(String oriUrl) {
        try {
            return URLDecoder.decode(oriUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("url解码异常:", e);
            return null;
        }
    }

    /**
     * 从容器中获取长链接
     *
     * @param url 解码后的短链接
     * @return java.lang.String
     * @author Irvin
     * @date 2021/10/9
     */
    private String getLong(String url) {
        return container.shortKey.get(url);
    }
}
