package com.jk.shorturl.service;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.jk.shorturl.Utils.Base62RadixUtil;
import com.jk.shorturl.Utils.SequenceUtil;
import com.jk.shorturl.config.ConfigMainUtil;
import com.jk.shorturl.dao.ShortDao;
import com.jk.shorturl.domain.ShortLongBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

/**
 * @author Jiang Jikun
 * ShortCode
 * @Description 短域名服务类，主要实现域名转换的逻辑
 */
@Service
public class ShortServiceImpl implements ShortService {

    private static final Logger logger = LoggerFactory.getLogger(ShortServiceImpl.class);

    @Autowired
    ConfigMainUtil configMainUtil;


    @Autowired
    @Qualifier("shortDaoMemory")
    ShortDao shortDao;

    @Override
    public String generalShortURL(String longURL) {
        String shortCode = shortDao.findShortCode(longURL);

        if (Objects.isNull(shortCode)) {
            //shortCode = Base62RadixUtil.to62RadixString((long) longURL.hashCode());
            //String salt = "";//generateSalt();
            //shortCode = Hashing.murmur3_32().hashString(salt + longURL, Charsets.UTF_8).toString();
            shortCode = Base62RadixUtil.to62RadixString(SequenceUtil.getNextId());

            ShortLongBean shortLongBean = new ShortLongBean();
            shortLongBean.setLongURL(longURL);
            shortLongBean.setShortCode(shortCode);

            shortDao.saveShortCode(shortLongBean);
            logger.info("info - i want convert to short url,i am {},long url is {}", shortCode, longURL);
        } else {
            logger.info("info - i get short url from db,i am {},long url is {}", shortCode, longURL);
        }

        return configMainUtil.getDomain() + shortCode;
    }

    @Override
    public String getLongURL(String shortCode) {
        String longURL = shortDao.findLongURL(shortCode);
        return longURL;
    }


}
