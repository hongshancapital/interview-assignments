package com.yuanyang.hongshan.service;

import com.yuanyang.hongshan.entity.RequestDTO;
import com.yuanyang.hongshan.entity.Result;

public interface UrlService {

	Result<String> generateShortURL(RequestDTO requestDTO);

	Result<String> getLongUrlByShortUrl(RequestDTO requestDTO);

}
