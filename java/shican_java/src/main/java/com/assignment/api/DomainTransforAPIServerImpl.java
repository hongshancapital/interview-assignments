package com.assignment.api;

import com.assignment.util.DomainMapperUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 域名转化服务API
 *
 * @Author: shican.sc
 * @Date: 2022/6/12 21:51
 * @see
 */
@Service
public class DomainTransforAPIServerImpl implements DomainTransforAPIServer{

    /**
     * 短域名转化为长域名
     *
     * @param shotDomain
     * @return
     */
    public String shotToLongDomain(String shotDomain) {
        if (StringUtils.isEmpty(shotDomain)) {
            //为空不处理
            return shotDomain;
        }

        return DomainMapperUtil.shotToLongdomainMapper.get(shotDomain);
    }


    /**
     * 长域名转化为短域名
     *
     * @param longDomainUrl
     * @return
     */
    public String longToShotDomain(String longDomainUrl) {
        if (StringUtils.isEmpty(longDomainUrl)) {
            return longDomainUrl;
        }

        String shotDomainUrl = "";
        if (longDomainUrl.startsWith(DomainMapperUtil.StartWithHTTP)) {
            shotDomainUrl = DomainMapperUtil.algorithmURLWithHttp(longDomainUrl);
        }else {
            shotDomainUrl = DomainMapperUtil.algorithmURLWithHttps(longDomainUrl);
        }

        DomainMapperUtil.shotToLongdomainMapper.put(shotDomainUrl, longDomainUrl);
        DomainMapperUtil.longToShotdomainMapper.put(longDomainUrl, shotDomainUrl);
        return shotDomainUrl;
    }
}
