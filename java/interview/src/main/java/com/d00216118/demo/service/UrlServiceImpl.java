package com.d00216118.demo.service;

import com.d00216118.demo.model.InfoUrl;
import com.d00216118.demo.repository.UrlRepository;
import com.google.common.hash.Hashing;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import javax.validation.constraints.NotEmpty;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Yu Chen
 * @email D00216118@student.dkit.ie
 * @date 2:47 下午 2021/4/2
 **/
@Service
public class UrlServiceImpl implements UrlService {

    private final UrlRepository urlRepository;

    public UrlServiceImpl(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Override
    public boolean checkUrlExist(@NotEmpty String url, Long userId) {
        List<InfoUrl> infoUrls = urlRepository.findByMd5Url(urlToMd5(url));
        InfoUrl infoUrl = null;
        if(infoUrls.size()>0) {
            for (InfoUrl x : infoUrls) {
                if (x.getUrl().compareToIgnoreCase(url) == 0 && x.getUserId() == userId) {
                    infoUrl = x;
                    break;
                }
            }
            if(infoUrl!=null){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public InfoUrl convertToTinyUrl(InfoUrl infoUrl) {

        //the first step is to add the MD5 value of URL
        String md5Url = urlToMd5(infoUrl.getUrl());
        infoUrl.setMd5Url(md5Url);
        InfoUrl saveInfoUrl = urlRepository.save(infoUrl);
        Long createdStamp = saveInfoUrl.getCreatedStamp();

        //The second step uses the MD5 value to combine the timestamp to generate an 8-bit Hash code
        String tinyUrl = Hashing.murmur3_32().hashString(md5Url + createdStamp.toString(), StandardCharsets.UTF_8).toString();
        saveInfoUrl.setTinyUrl(tinyUrl);
        return urlRepository.saveAndFlush(saveInfoUrl);
    }

    @Override
    @Cacheable(value="infoUrl",unless="#result == null")
    public InfoUrl getUrlByTinyUrl(String tinyUrl, Long userId) {
        return urlRepository.findByTinyUrlAndUserId(tinyUrl,userId);
    }

    static String urlToMd5(String url) {
        return DigestUtils.md5DigestAsHex(url.getBytes());
    }

}
