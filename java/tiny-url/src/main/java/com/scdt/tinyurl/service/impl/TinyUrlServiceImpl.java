package com.scdt.tinyurl.service.impl;

import com.scdt.tinyurl.common.ErrorCode;
import com.scdt.tinyurl.config.AppConfig;
import com.scdt.tinyurl.dao.UrlCache;
import com.scdt.tinyurl.dao.UrlStorage;
import com.scdt.tinyurl.exception.GlobalException;
import com.scdt.tinyurl.manager.SequenceManager;
import com.scdt.tinyurl.model.UrlEntity;
import com.scdt.tinyurl.service.TinyUrlService;
import com.scdt.tinyurl.util.CodecUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class TinyUrlServiceImpl implements TinyUrlService {

    @Autowired
    private UrlStorage urlStorage;

    @Autowired(required = false)
    private UrlCache urlCache;

    @Autowired
    private SequenceManager sequenceManager;

    @Autowired
    private AppConfig appConfig;


    @Override
    public String fetchLongUrl(String tinyUrl) {

        String decodedId = CodecUtil.decode62(tinyUrl,appConfig.getCodecDict(),appConfig.getMaxIdLength());
        log.info("decoded Id: {}",decodedId);

        //检查ID是否为当前机器生成(brokerId可以为空)
        String brokerId = appConfig.getBrokerId();
        if(!decodedId.startsWith(brokerId)) {
            throw new GlobalException(ErrorCode.ID_MOVED_ERROR);
        }

        String result = urlStorage.get(tinyUrl);

        if(result == null) {
            throw new GlobalException(ErrorCode.TINY_URL_NOT_FOUND_OR_EXPIRED);
        }
        return result;
    }

    @Override
    public String fetchTinyUrl(UrlEntity urlEntity) {

        log.info(appConfig.toString());

        if(urlCache!= null && urlCache.get(urlEntity.getLongUrl()) != null) {
            return urlCache.get(urlEntity.getLongUrl());
        }
        //生成唯一ID
        long id = sequenceManager.getNextId();

        //转换成8位62进制编码
        String tinyUrl = CodecUtil.encode62(id,appConfig.getCodecDict(),appConfig.getCodecLength());

        urlStorage.put(tinyUrl,urlEntity.getLongUrl());

        //urlCache未启用时跳过
        if(urlCache != null) {
            urlCache.put(urlEntity.getLongUrl(),tinyUrl);
        }

        return tinyUrl;
    }


}
