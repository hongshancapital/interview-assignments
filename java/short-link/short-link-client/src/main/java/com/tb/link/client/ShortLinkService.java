package com.tb.link.client;

import com.tb.link.client.domain.Result;
import com.tb.link.client.domain.reponse.ShortLinkOriginResponse;
import com.tb.link.client.domain.reponse.ShortLinkResponse;
import com.tb.link.client.domain.request.LongLinkRequest;
import com.tb.link.client.domain.request.ShortLinkRequest;

/**
 * @author andy.lhc
 * @date 2022/4/16 12:54
 */
public interface ShortLinkService {

    /**
     * 通过长链生成短链
     * @param request
     * @return
     */
    Result<ShortLinkResponse>  generalShortLink(LongLinkRequest request) ;

    /**
     * 通过短链恢复长链
     * @param request
     * @return
     */
    Result<ShortLinkOriginResponse>  recoverShortLink(ShortLinkRequest request) ;

}

