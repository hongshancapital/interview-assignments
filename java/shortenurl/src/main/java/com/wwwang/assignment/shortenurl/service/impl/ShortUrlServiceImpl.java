package com.wwwang.assignment.shortenurl.service.impl;

import com.wwwang.assignment.shortenurl.compress.AbstractCompress;
import com.wwwang.assignment.shortenurl.entity.ShortUrl;
import com.wwwang.assignment.shortenurl.exception.BizException;
import com.wwwang.assignment.shortenurl.repository.LongUrlRepository;
import com.wwwang.assignment.shortenurl.repository.ShortUrlRepository;
import com.wwwang.assignment.shortenurl.service.IShortenUrlService;
import com.wwwang.assignment.shortenurl.shorten.AbstractShorten;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShortUrlServiceImpl implements IShortenUrlService {

    @Autowired
    private AbstractShorten shorten;
    @Autowired
    private AbstractCompress compress;
    @Autowired
    private LongUrlRepository longUrlRepository;
    @Autowired
    private ShortUrlRepository shortUrlRepository;

    @Override
    public String getShortUrl(String longUrl) {
        if(StringUtils.isBlank(longUrl)){
            throw new BizException("非法请求参数");
        }
        //step1 压缩长链接
        byte[] compressedUrlBytes = compress.compress(longUrl.getBytes());
        Object compressedUrlObj = longUrlRepository.bytesToLongUrl(compressedUrlBytes);
        //step2 去仓库查找短链接id
        String shortUrlContent = (String) shortUrlRepository.get(compressedUrlObj);
        //step3 根据查询结果判断是否需要生成新的短链接
        if(StringUtils.isBlank(shortUrlContent)){//仓库中不存在短链接
            ShortUrl shortUrl = shorten.shorten(longUrl);
            shortUrlContent = shortUrl.getContent();
            //将生成的短链接放入仓库
            shortUrlRepository.put(compressedUrlObj,shortUrl.getContent());
            longUrlRepository.put(shortUrl.getContent(),compressedUrlObj);
        }
        //step4 返回短链接
        return shortUrlContent;
    }

    @Override
    public String getLongUrl(String shortUrl) {
        if(StringUtils.isBlank(shortUrl)){
            throw new BizException("非法请求参数");
        }
        //step2 根据短链接id从仓库查找对应压缩后的长链接
        Object compressedUrlObj = longUrlRepository.get(shortUrl);
        if(compressedUrlObj == null){
            throw new BizException("无效短链接");
        }
        //step3 解压缩长链接
        byte[] urlBytes = compress.deCompress(longUrlRepository.getLongUrlBytes(compressedUrlObj));
        //step4 返回长链接
        return new String(urlBytes);
    }
}
