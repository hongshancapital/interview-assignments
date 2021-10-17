package com.zhangzheng.homework.service;

import com.zhangzheng.homework.entity.UrlMap;
import com.zhangzheng.homework.repository.UrlMapRepository;
import com.zhangzheng.homework.utils.NumConvertUtils;
import com.zhangzheng.homework.utils.SnowFlake;
import com.zhangzheng.homework.utils.UrlValidator;
import lombok.extern.slf4j.Slf4j;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangzheng
 * @version 1.0
 * @description: 生成短url和还原长url函数
 * @date 2021/10/9 上午10:34
 */
@Service
@Slf4j
public class ConvertService {
    @Resource
    private SnowFlake idGenerator;

    @Autowired
    private UrlMapRepository urlMapRepository;
    @Autowired
    private AsyncService asyncService;

    /**
     * @description: 根据输入的长url，生成对应的短url
     * @param: longUrl
     * @return: java.lang.String
     * @author zhangzheng
     * @date: 2021/10/4 下午3:12
     */
    public String generate(String longUrl) {
        log.info("原始longUrl={}", longUrl);
        if (StringUtils.isEmpty(longUrl)) {
            log.error("url地址为空，longUrl={}", longUrl);
            throw new RuntimeException("url地址为空,请提交后再试,url=" + longUrl);
        }
        longUrl = longUrl.trim();
        //效验url格式
        if (!UrlValidator.checkUrl(longUrl)) {
            log.error("url格式不正确,longUrl={}", longUrl);
            throw new RuntimeException("url格式不正确,请修正后再试");
        }

        String shortUrl;
        //先检查一下库中是否存在结果,如果存在返回上次结果
        UrlMap urlMap = getUrlMapByLong(longUrl);
        if (urlMap != null) {
            return urlMap.getShortUrl();
        }
        //如果数据库中不存在结果直接生成一个新的短地址
        long nextId = idGenerator.nextId(null);
        // 转换为62进制字符结果
        shortUrl = NumConvertUtils.convertToOther(nextId, 62);
        log.info("转换结果shortUrl={}", shortUrl);
        //对应关系入库,H2
        asyncService.saveUrlMap(longUrl, shortUrl);
        return shortUrl;
    }

    /**
     * @description: 根据输入的短url找到对应的长url
     * @param: shortUrl
     * @return: java.lang.String
     * @author zhangzheng
     * @date: 2021/10/4 下午3:13
     */
    public String revertUrl(String shortUrl) {
        log.info("开始还原，shortUrl={}", shortUrl);
        String shortKey = shortUrl.substring(shortUrl.lastIndexOf("/") + 1);
        UrlMap urlMap = getUrlMapByShort(shortKey);
        if (urlMap != null) {
            log.info("还原结果，longUrl={}", urlMap.getLongUrl());
            return urlMap.getLongUrl();
        }
        return null;
    }


    /**
     * @description: 根据shortUrl返回对应的长url地址
     * @param: shortUrl
     * @return: com.zhangzheng.homework.entity.UrlMap
     * @author zhangzheng
     * @date: 2021/10/9 下午2:20
     */
    public UrlMap getUrlMapByShort(String shortUrl) {
        log.info("根据短url还原长url，shortUrl={}", shortUrl);
        if (StringUtils.isEmpty(shortUrl)) {
            return null;
        }
        List<UrlMap> list = urlMapRepository.getResultByShortUrl(shortUrl);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    /**
     * @description: 根据longUrl返回对应的短url地址
     * @param: longUrl
     * @return: com.zhangzheng.homework.entity.UrlMap
     * @author zhangzheng
     * @date: 2021/10/9 下午2:20
     */
    public UrlMap getUrlMapByLong(String longUrl) {
        log.info("根据长url查看是否存在短url，longUrl={}", longUrl);
        if (StringUtils.isEmpty(longUrl)) {
            return null;
        }
        List<UrlMap> list = urlMapRepository.getResultByLongUrl(longUrl);
        if (list != null && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    public static void main(String[] args) {
        Options options = new OptionsBuilder().include(ConvertService.class.getName()).forks(1).build();
        try {
            new Runner(options).run();
        } catch (RunnerException e) {
            e.printStackTrace();
        }
    }

}
