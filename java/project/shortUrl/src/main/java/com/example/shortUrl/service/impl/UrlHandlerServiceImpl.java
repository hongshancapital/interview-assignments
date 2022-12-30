package com.example.shortUrl.service.impl;

import com.example.shortUrl.dao.CacheMap;
import com.example.shortUrl.dao.CacheMapFactory;
import com.example.shortUrl.pojo.Result;
import com.example.shortUrl.pojo.ResultEnum;
import com.example.shortUrl.service.UrlHandlerService;
import com.example.shortUrl.utils.Number64;
import com.example.shortUrl.utils.SnowFlake;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author HOPE
 * @Description url处理实现类
 * @Date 2022/4/28 20:03
 */
@Service
public class UrlHandlerServiceImpl implements UrlHandlerService {
    private static final Logger log = LoggerFactory.getLogger(UrlHandlerServiceImpl.class);
    private  SnowFlake snowFlake=new SnowFlake();
    private Number64 number64=new Number64();
    //单例模式获取缓存map
    private CacheMap cacheMap = CacheMapFactory.newCacheMap();

    @Override
    public Result<String> getShortUrl(String longUrl) {
        Result<String> result = null;
        try{
            final String md5 = DigestUtils.md5Hex(longUrl);
            String shortUrl = (String)cacheMap.getValue(md5);
            if(shortUrl == null){
                synchronized (md5.getClass()){
                    shortUrl = (String)cacheMap.getValue(md5);
                    if(shortUrl == null){
                        //发号器生成id
                        long id = snowFlake.nextId();
                        shortUrl = number64.encoding(id);
                        //缓存时间30分钟
                        cacheMap.setEx(shortUrl,longUrl,1800);
                        cacheMap.setEx(md5,shortUrl,1800);
                    }else{
                        //更新缓存时间
                        cacheMap.setEx(shortUrl,longUrl,1800);
                        cacheMap.setEx(md5,shortUrl,1800);
                    }
                }
            }else{
                //更新缓存时间
                cacheMap.setEx(shortUrl,longUrl,1800);
                cacheMap.setEx(md5,shortUrl,1800);
            }
            List<String> list=new ArrayList<>();
            list.add(shortUrl);
            result = new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(), list);
        }catch (Exception e){
          log.error("获取短链接出现异常",e);
          result = new Result<>(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg(), null);
        }
        return result;
    }

    @Override
    public Result<String> getLongUrl(String shortUrl) {
        Result<String> result = null;
        try{
            String longUrl = null;
            if(cacheMap.containsKey(shortUrl)){
                longUrl = (String)cacheMap.getValue(shortUrl);
                //更新缓存时间
                String md5 = DigestUtils.md5Hex(longUrl);
                cacheMap.setEx(shortUrl,longUrl,1800);
                cacheMap.setEx(md5,shortUrl,1800);
                List<String> list = new ArrayList<>();
                list.add(longUrl);
                result = new Result<>(ResultEnum.SUCCESS.getCode(),ResultEnum.SUCCESS.getMsg(), list);
            }else{
                result = new Result<>(ResultEnum.NOT_EXISTS.getCode(),ResultEnum.NOT_EXISTS.getMsg(), null);
            }
        }catch (Exception e){
            log.error("获取长链接出现异常",e);
            result = new Result<>(ResultEnum.ERROR.getCode(),ResultEnum.ERROR.getMsg(), null);
        }
        return result;
    }


}

