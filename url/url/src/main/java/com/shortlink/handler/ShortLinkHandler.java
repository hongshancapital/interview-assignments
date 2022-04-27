package com.shortlink.handler;


import com.shortlink.entity.CreateShortLinkRequest;
import com.shortlink.entity.FetchOriginalUrlRequest;
import com.shortlink.entity.ShortLinkEntity;

public interface ShortLinkHandler {

    /**
     * 生成短链
     * @param request
     * @return
     */
    ShortLinkEntity createShortLink(CreateShortLinkRequest request);

    /**
     * 通过短链获取正常url
     * @param request
     * @return
     */
    ShortLinkEntity fetchUrlByShortLink(FetchOriginalUrlRequest request);
}
