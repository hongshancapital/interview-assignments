package com.wangxiao.shortlink.applicaiton;

public interface ShortLinkService {
    /**
     * 长链接编码服务
     *
     * @param longLink
     * @return 短链接
     * @throws com.wangxiao.shortlink.infrastructure.common.StoreOverFlowException
     */
    String encodeUrl(String longLink);

    /**
     * 短链接解码服务
     *
     * @param shortLink
     * @return 长链接
     */
    String decodeUrl(String shortLink);

}
