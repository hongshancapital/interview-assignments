package com.wwc.demo.service.impl;

import com.wwc.demo.common.ResponseEnum;
import com.wwc.demo.common.ResponseResult;
import com.wwc.demo.constant.Constants;
import com.wwc.demo.service.ShortUrlService;
import com.wwc.demo.util.CacheUtil;
import com.wwc.demo.util.ShortUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ShortUrlServiceImpl implements ShortUrlService {
    //生成短连接的前缀
    @Value("${short.prefix}")
    private String shortPrefix;
    @Override
    public ResponseResult getShortUrl(String longUrl) {
        try{
           //String longUrl=request.getLongUrl();
            String shortCode= "";
            //若长链接已生成过一次短码，则直接从map中根据长链接拿短码，不用再生成一次
            if(CacheUtil.isContainValue(longUrl)){
                log.info(">>> 长链接已存在："+longUrl);
                shortCode=CacheUtil.getKeyByValue(longUrl);
                log.info(">>> 长链接已有短码："+shortCode);
            }else{
                //随机生成短码，若与map的key重复，则重新生成，直到不与map的key值重复
                do{
                    shortCode= ShortUtil.shortCode(Constants.INDEX_8);
                }while(CacheUtil.isContainKey(shortCode));
                //将新的短码 - 长链接键值对存储到map中
                CacheUtil.put(shortCode,longUrl);
            }
            return new ResponseResult(ResponseEnum.SUCCESS,shortPrefix+shortCode);
        }catch (Exception e){
            log.error(">>> getShortUrl 异常：",e);
            return new ResponseResult(ResponseEnum.EXCEPT,null);
        }
    }

    @Override
    public ResponseResult getLongUrl(String shortCode) {
        try{
            //短码作为key值从map中获取长链接
            Object object=CacheUtil.get(shortCode);
            if(object!=null){
                return new ResponseResult(ResponseEnum.SUCCESS,object.toString());
            }else {
                return new ResponseResult(ResponseEnum.NOT_EXISTS,null);
            }
        }catch (Exception e){
            log.error(">>> getLongUrl 异常：",e);
            return new ResponseResult(ResponseEnum.EXCEPT,null);
        }
    }
}
