package com.sequoia.interview.service;

import com.sequoia.interview.idGenerator.IdGenerator;
import com.sequoia.interview.utils.NumberConvertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 17612735387@163.com
 * @date 2022/8/13 11:25
 **/
@Service
public class ShortDomainService {

    public static String SHORT_URL_PREFIX = "http://t/";

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private DomainDataCache dataCache;

    /**
    * @Description: generate short link from long link
    * @Param: longLink
    * @return:  String
    * @Author: yhzhang
    * @Date: 2022/8/13
    */
    public String generateShortLink(String longLink) throws Exception {
        Long id = idGenerator.generateId();
        if (id == null) {
            throw new Exception("号码已经用完");
        }
        String encode = NumberConvertUtil.encode(id, 62, 11);
        int length = encode.length();
        encode = encode.substring(length - 8, length);
        encode = SHORT_URL_PREFIX + encode;
        dataCache.putLongUrl(encode, longLink);
        return encode;
    }

    public String findLongLink(String shortLink) {
        String longLink = dataCache.getLongUrl(shortLink);
        if (StringUtils.isEmpty(longLink)){
            throw new IllegalArgumentException("短链接不合法");
        }
        return longLink;
    }
}
