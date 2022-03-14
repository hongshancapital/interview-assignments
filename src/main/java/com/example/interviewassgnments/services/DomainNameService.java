/**
 * this is a test project
 */

package com.example.interviewassgnments.services;

import com.example.interviewassgnments.annotation.ResponseResult;
import com.example.interviewassgnments.entitys.DacService;
import com.example.interviewassgnments.entitys.DomainNameOptions;
import com.example.interviewassgnments.utils.BusinessException;
import com.example.interviewassgnments.utils.CreateShortCodeUtils;
import com.example.interviewassgnments.utils.ResultEnum;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * 长域名转写及域名查询实现类
 *
 * @Auther: maple
 * @Date: 2022/1/19 9:47
 * @Description: com.example.interviewassgnments.services
 * @version: 1.0
 */
@Service
@ResponseResult
public class DomainNameService {
    @Value("${system.domain.name}")
    private String[] domainNames;

    @Value("${system.domain.encoding.length}")
    private int encodingLength = 4;

    private DomainNameOptions domainOptions = (DomainNameOptions) ApplicationContextUtils.getById("domainOptions");
    private DacService dacService = (DacService) ApplicationContextUtils.getById("dacService");
    private Integer maxInter = dacService.getCounterThreshold();

    /**
     * 获取domainName
     * 当输入值超过阀值maxInter时，切换域名
     *
     * @param num Integer 待转换的数字
     * @return 返回域名
     */
    private String getDomainName(Integer num) {
        if (num < maxInter) {
            return domainNames[0];
        }
        for (int i = 1; i < domainNames.length; i++) {
            if (maxInter * i < num && maxInter * (i + 1) > num) {
                return domainNames[i];
            }
        }
        return domainNames[0];
    }

    //
    public String getShortDomainLink(String longLink) {
        for (int i = 0; i < domainNames.length; i++) {
            synchronized (this) {
                if (!dacService.isItemFull(domainNames[i])) {
                    int value = dacService.getCounterNum(domainNames[i]);
                    String shortLink = CreateShortCodeUtils.integer2ShortCode(value);
                    domainOptions.add(domainNames[i], shortLink, longLink);
                    dacService.putItemKey(domainNames[i]);
                    return domainNames[i] + shortLink;
                }
            }
        }
        throw new BusinessException(ResultEnum.EXCEPTION_ILLEGAL_CHARACTER);
    }

    public String getFullLink(String shortLink) {
        String domainName = shortLink.substring(0,shortLink.indexOf("/")+1);
        String shortCode = shortLink.substring(shortLink.indexOf("/")+1);
        if (shortCode.length() == encodingLength &&  Arrays.asList(domainNames).contains(domainName)) {
            return domainOptions.find(domainName, shortCode);
        }
        throw new BusinessException(ResultEnum.PARAM_IS_INVALID);
    }
}
