package com.getao.urlconverter.service.impl;

import com.getao.urlconverter.dto.vo.GetLongUrlVO;
import com.getao.urlconverter.dto.vo.GetShortUrlVO;
import com.getao.urlconverter.service.UrlConverterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service
public class UrlConverterServiceImpl implements UrlConverterService {


    @Override
    public GetShortUrlVO getShortUrl(String longUrl) {

        return null;
    }

    @Override
    public GetLongUrlVO getLongUrl(String shortUrl) {

        return null;
    }
}
