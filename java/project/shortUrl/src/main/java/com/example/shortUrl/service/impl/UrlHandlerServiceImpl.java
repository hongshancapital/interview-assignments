package com.example.shortUrl.service.impl;

import com.example.shortUrl.dao.UrlMaps;
import com.example.shortUrl.pojo.Result;
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

    @Override
    public Result<String> getShortUrl(String longUrl) {
        Result<String> result = new Result<>();
        try{
            final String md5 = DigestUtils.md5Hex(longUrl);
            String shortUrl = UrlMaps.md5Urls.get(md5);
            if(shortUrl == null){
                synchronized (md5.getClass()){
                    shortUrl = UrlMaps.md5Urls.get(md5);
                    if(shortUrl == null){
                        //发号器生成id
                        long id = snowFlake.nextId();
                        shortUrl = number64.encoding(id);
                        UrlMaps.shortUrls.put(shortUrl,longUrl);
                        UrlMaps.md5Urls.put(md5,shortUrl);
                    }
                }
            }
            result.setCode(200);
            result.setMsg("success");
            List<String> list=new ArrayList<>();
            list.add(shortUrl);
            result.setData(list);

        }catch (Exception e){
          log.error("获取短链接出现异常",e);
          result.setCode(500);
          result.setMsg("获取短链接出现异常");
        }
        return result;
    }

    @Override
    public Result<String> getLongUrl(String shortUrl) {
        Result<String> result = new Result<>();
        try{
            String longUrl = null;
            if(UrlMaps.shortUrls.containsKey(shortUrl)){
                longUrl = UrlMaps.shortUrls.get(shortUrl);
                result.setCode(200);
                result.setMsg("success");
                List<String> list=new ArrayList<>();
                list.add(longUrl);
                result.setData(list);
            }else{
                result.setCode(200);
                result.setMsg("短链接不存在，无法获取长链接");
            }
        }catch (Exception e){
            log.error("获取长链接出现异常",e);
            result.setCode(500);
            result.setMsg("获取长链接出现异常");
        }
        return result;
    }


}

