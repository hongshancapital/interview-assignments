package com.hongshang.shorturlimpl;

import com.hongshang.shorturlinterface.IShortUrlService;
import com.hongshang.shorturlmodel.api.IUrlDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

/**
 * 地址维护接口实现类
 */
@Service("shortUrlService")
public class ShortUrlService implements IShortUrlService {

    @Autowired
    private IUrlDao urlDao;

    @Override
    public String transformToShortUrl(String longUrl) {
        final String md5 = DigestUtils.md5Hex(longUrl);
        String shortUrl = urlDao.getByKey(md5);
        if (shortUrl == null){
            synchronized (md5.intern()){
                shortUrl = urlDao.getByKey(md5);
                if (shortUrl == null){
                    // 为避免产生的短址重复，可以进行10次的重新创建短地址
                    for(int index=0;index<10;index++) {
                        shortUrl = urlDao.getShortUrl();
                        if(urlDao.putKeyValue(shortUrl, longUrl)) {
                            urlDao.putKeyValue(md5, shortUrl);
                            break;
                        }
                    }
                }
            }
        }
        return shortUrl;
    }

    @Override
    public String getLongUrlByShortUrl(String shortUrl) {
        return urlDao.getByKey(shortUrl);
    }

}
