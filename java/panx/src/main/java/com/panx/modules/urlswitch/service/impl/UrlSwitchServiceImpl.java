package com.panx.modules.urlswitch.service.impl;

import com.panx.exception.UrlSwitchException;
import com.panx.modules.urlswitch.hanlder.UrlSwitchHanlder;
import com.panx.modules.urlswitch.service.UrlSwitchService;
import com.panx.modules.urlswitch.vo.UrlVo;
import com.panx.utils.IdGenerator;
import com.panx.utils.UrlSwitchUtils;
import org.springframework.stereotype.Service;

/**
 * @author PanX
 * @date 2021-07-01
 * 长短域名转化Service实现类
 *
 */
@Service
public class UrlSwitchServiceImpl implements UrlSwitchService {
    private static final IdGenerator idGenerator = new IdGenerator();
    /**
     * 短域名转为长域名
     * @Param 包含长域名的UrlVo urlInfo
     * @return 包含结果信息及接口运行信息的Result
     */
    public UrlVo urlShortToLong(UrlVo urlInfo) throws UrlSwitchException {
        String longUrl = urlInfo.getLongUrl();
        String shortUrl = urlInfo.getShortUrl();
        if(null==longUrl&&null!=shortUrl){
            String urlHead = UrlSwitchUtils.getUrlHead(shortUrl);
            longUrl= UrlSwitchHanlder.getUrlMatch(shortUrl.replace(urlHead,""));
            if(null==longUrl){
                throw new UrlSwitchException("暂无匹配数据");
            }
            longUrl = urlHead+longUrl;
            urlInfo.setLongUrl(longUrl);
            return urlInfo;
        }
        else {
            throw new UrlSwitchException("请输入长域名或短域名");
        }
    }

    /**
     * 短域名转为长域名
     * @Param 包含短域名的UrlVo urlInfo
     * @return 包含结果信息及接口运行信息的Result
     */
    public UrlVo urlLongToShort(UrlVo urlInfo) throws UrlSwitchException {
        String longUrl = urlInfo.getLongUrl();
        String shortUrl = urlInfo.getShortUrl();
        if(null==shortUrl&&null!=longUrl){
            String urlHead = UrlSwitchUtils.getUrlHead(longUrl);
            shortUrl= UrlSwitchHanlder.getUrlMatch(longUrl.replace(urlHead,""));
            if(null==shortUrl){
                shortUrl = UrlSwitchUtils.idToShortUrl(idGenerator.nextId());
                UrlSwitchHanlder.setUrlMatch(longUrl.replace(urlHead,""),shortUrl);
            }
            shortUrl = urlHead+shortUrl;
            urlInfo.setShortUrl(shortUrl);
            return urlInfo;
        }
        else {
            throw new UrlSwitchException("请输入长域名或短域名");
        }
    }

    /**
     * 自动判断并转换长短域名
     * @Param 包含长域名或短域名的UrlVo urlInfo
     * @return 包含结果信息及接口运行信息的Result
     */
    public UrlVo urlSwitch(UrlVo urlInfo) throws UrlSwitchException {
        String longUrl = urlInfo.getLongUrl();
        String shortUrl = urlInfo.getShortUrl();
        if(null==longUrl&&null!=shortUrl){
            return urlShortToLong(urlInfo);
        }else if(null == shortUrl){
            return urlLongToShort(urlInfo);
        }else {
            throw new UrlSwitchException("请输入长域名或短域名");
        }
    }

}
