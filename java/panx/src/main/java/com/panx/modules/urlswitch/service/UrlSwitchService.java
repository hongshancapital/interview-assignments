package com.panx.modules.urlswitch.service;

import com.panx.exception.UrlSwitchException;
import com.panx.modules.urlswitch.vo.UrlVo;

/**
 * @author PanX
 * @date 2021-07-01
 * 长短域名转化Service
 *
 */
public interface UrlSwitchService {
    /**
     * 短域名转为长域名
     * @Param 包含长域名的UrlVo urlInfo
     * @return 包含结果信息及接口运行信息的Result
     */
    UrlVo urlShortToLong(UrlVo urlInfo) throws UrlSwitchException;

    /**
     * 短域名转为长域名
     * @Param 包含短域名的UrlVo urlInfo
     * @return 包含结果信息及接口运行信息的Result
     */
    UrlVo urlLongToShort(UrlVo urlInfo) throws UrlSwitchException;

    /**
     * 自动判断并转换长短域名
     * @Param 包含长域名或短域名的UrlVo urlInfo
     * @return 包含结果信息及接口运行信息的Result
     */
    UrlVo urlSwitch(UrlVo urlInfo) throws UrlSwitchException;

}
