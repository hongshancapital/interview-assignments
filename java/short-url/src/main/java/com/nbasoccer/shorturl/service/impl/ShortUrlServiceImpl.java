package com.nbasoccer.shorturl.service.impl;

import com.nbasoccer.shorturl.manager.DatabaseManager;
import com.nbasoccer.shorturl.service.IShortUrlService;
import com.nbasoccer.shorturl.utils.LRUCache;
import com.nbasoccer.shorturl.utils.NumericConvertUtils;
import com.nbasoccer.shorturl.utils.SnowFlake;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class ShortUrlServiceImpl implements IShortUrlService {

    //短链接根域名
    @Value("${web.uri}")
    private String webUri;

    /*
     *  模拟数据：数据中心数量，模拟得出数据中心ID
     */
    @Value("${data.center.count}")
    private int dataCenterCount;

    /*
     *  模拟数据：位于某数据中心下的机器ID，模拟得出数据中心ID
     */
    @Value("${per.center.machine.count}")
    private int machineCountPerCenter;

    @Autowired
    private DatabaseManager databaseManager;

    @Override
    public String convertShortUrl(String originUrl) {

        //模拟请求自某个数据中心的某台机器
        int dataCenterId = new Random().nextInt(dataCenterCount);
        int machineId = new Random().nextInt(machineCountPerCenter);

        String shortUrlFromCache = databaseManager.getKeyByValue(originUrl);
        if(StringUtils.isNotBlank(shortUrlFromCache)) {
            return shortUrlFromCache;
        }
        SnowFlake snowFlake = new SnowFlake(dataCenterId, machineId);
        String code = NumericConvertUtils.toOtherNumberSystem(snowFlake.nextId(), 62);
        databaseManager.getAndStoreUrlValue(code, originUrl);

        System.out.println("cache element count:" + databaseManager.getElementCount());
        return webUri + code;

    }

    @Override
    public String convertLongUrl(String shortUrl) {

        String code = StringUtils.substringAfterLast(shortUrl, "/");
        return databaseManager.getUrlValue(code);
    }
}
