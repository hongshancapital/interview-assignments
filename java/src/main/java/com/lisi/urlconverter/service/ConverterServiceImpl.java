package com.lisi.urlconverter.service;

import com.lisi.urlconverter.enumeration.UCErrorType;
import com.lisi.urlconverter.model.UCException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * @description: 域名转换服务Service的实现类
 * @author: li si
 */

@Service
public class ConverterServiceImpl implements ConverterService, Runnable, InitializingBean {
    @Resource(name = "generateUrlBasedOnTime")
    private GenerateUrlService generateUrlService;

    @Resource(name = "urlMappingBasedOnHashMap")
    private UrlMappingService urlMappingService;

    /** 是否还能存储新的映射数据 **/
    private volatile boolean canWrite;

    private volatile boolean isRunning;

    /** 定时检查任务的执行周期 **/
    @Value("${urlconverter.jvm.check.interval}")
    private long checkMemoryInterval;

    @Override
    public String convert(String longUrl) {
        if(!canWrite){
            throw new UCException(UCErrorType.MEMORY_NOT_ENOUGH_EXCEPTION);
        }
        String shortUrl = generateUrlService.getUrl();
        urlMappingService.saveMapping(longUrl, shortUrl);
        return shortUrl;
    }

    @Override
    public String getLongUrl(String shortUrl) {
        String longUrl = urlMappingService.getMapping(shortUrl);
        if(longUrl == null){
            throw new UCException(UCErrorType.URL_NOT_FOUND_EXCEPTION);
        }
        return longUrl;
    }

    /**
     * 定期执行内存检查任务
     * 根据当前内存情况，设置canWrite值
     */
    @Override
    public void run() {
        while(isRunning){
            boolean checkResult = urlMappingService.isEnough();
            if(checkResult && !canWrite){
                canWrite = true;
            }
            if(!checkResult && canWrite){
                canWrite = false;
            }
            try {
                Thread.sleep(checkMemoryInterval);
            } catch (Exception e){
                e.printStackTrace();
                break;
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        canWrite = true;
        isRunning = true;
        new Thread(this).start();
    }

    @PreDestroy
    public void onDestroy() {
        isRunning = false;
    }
}
