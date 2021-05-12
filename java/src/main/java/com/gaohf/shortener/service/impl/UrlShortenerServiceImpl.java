package com.gaohf.shortener.service.impl;

import com.gaohf.shortener.commons.response.ResponseResult;
import com.gaohf.shortener.service.IUrlShortenerService;
import com.gaohf.shortener.utils.VerificationUrl;
import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Gaohf
 * @create: 2021-05-11 17:49:51
 **/
@Service
@Slf4j
public class UrlShortenerServiceImpl implements IUrlShortenerService {

    private Map<String,String> cmap = new ConcurrentHashMap(4096);
    private Map<String,String> gmap = new ConcurrentHashMap(4096);
    private class Lock{};

    @Override
    public ResponseResult create(String url){

        //验证url是否合法
        boolean validUrl = VerificationUrl.isValidUrl(url);
        if (!validUrl){
            return ResponseResult.fail("请确定url是否可用");
        }

        UrlValidator urlValidator = new UrlValidator(
                new String[]{"http", "https"}
        );
        if (urlValidator.isValid(url)) {
            String id = Hashing.murmur3_32().hashString(url, StandardCharsets.UTF_8).toString();
            log.debug("URL Id generated: {}", id);
            synchronized (new Lock()) {
                cmap.put(url, id);
            }
            synchronized (new Lock()){
                gmap.put(id,url);
            }
            return ResponseResult.success(id);
        }
        return ResponseResult.fail("生成短链接失败，请稍后再试");
    }

    @Override
    public ResponseResult getUrl(String id) {
        if(null == id || "".equals(id)){
            return ResponseResult.fail("短链接id不能为空");
        }
        String url = gmap.get(id);
        if(null == url || ("".equals(url))){
            return ResponseResult.fail("未获取到长链接");
        }
        log.debug("URL Retrieved: {}", url);
        return ResponseResult.success(url);
    }
}
