package com.scdt.china.url.mapping.service.impl;

import com.scdt.china.url.mapping.Constants;
import com.scdt.china.url.mapping.service.IUrlMappingService;
import com.scdt.china.url.mapping.util.RandomStrUtil;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UrlMappingServiceImpl implements IUrlMappingService {

    /**
     * url 映射表，key：转换后的url, value：转换前的url
     */
    private Map<String,String> shortUrlMap = new ConcurrentHashMap<String,String>();

    /**
     * url 映射表，key：转换前的url, value：转换后的url
     */
    private Map<String,String> originalUrlMap = new ConcurrentHashMap<String,String>();


    @Override
    public String getShortUrl(String originalUrl) {
        //从内存中取转换后的url
        String shortUrl = originalUrlMap.get(originalUrl);
        if(shortUrl==null && originalUrlMap.size()< Constants.MAX_STORAGE_SIZE){
            while(true){
                //循环生成短链接，直到找到未生成过的短链接
                shortUrl = RandomStrUtil.getRandomStr(Constants.SHORT_URL_LENGTH);
                //设置映射关系，短链接 => 长链接
                String existShortMapOriginalUrl = shortUrlMap.putIfAbsent(shortUrl,originalUrl);
                if(existShortMapOriginalUrl == null){
                    //校验短url是否已存在，如不存在则映射成功。跳出循环
                    break;
                }
            }

            //存入长链接=>短链接映射
            String existUrl = originalUrlMap.putIfAbsent(originalUrl, shortUrl);
            if (existUrl!=null){
                //避免并发过程中一个长链接对应多个不同的短链接,
                shortUrlMap.remove(shortUrl);
                //将最先存入的url返回
                shortUrl = existUrl;
            }

            //添加前缀域名
            shortUrl = Constants.SHORT_URL_DOMAIN+shortUrl;
        }

        if(shortUrl==null){
            //转换失败。避免系统异常，此时返回原始url.或者可使用先进先出，以及最少使用原则来限制整个map的内存
            shortUrl = originalUrl;
        }
        return shortUrl;
    }

    @Override
    public String getOrinigalUrl(String shortUrl) {
        //去掉前缀
        if(shortUrl.startsWith(Constants.SHORT_URL_DOMAIN)){
            shortUrl = shortUrl.replace(Constants.SHORT_URL_DOMAIN,"");
        }
        //获取原始域名
        String orinigalUrl = shortUrlMap.get(shortUrl);
        if(orinigalUrl == null){
            //未获取到，直接使用原域名
            orinigalUrl = shortUrl;
        }
        return orinigalUrl;
    }
}
