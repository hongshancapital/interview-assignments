package demo.curURL.service;

import demo.dao.URLDao;
import demo.dao.URLDaoEnum;
import demo.dao.URLDaoFactory;
import demo.entity.URLEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CurURLService implements ICurURLService {
    private final URLDaoFactory urlDaoFactory;

    @Autowired
    public CurURLService(URLDaoFactory urlDaoFactory) {
        this.urlDaoFactory = urlDaoFactory;
    }

    // redis/mysql 变量考虑后续扩展用
    @Value("${url.dao.cache:true}")
    private boolean needCache;
    @Value("${url.dao.redis:false}")
    private boolean needRedis;
    @Value("${url.dao.mysql:true}")
    private boolean needMysql;

    @Override
    public URLEntity find(String curURL) {
        return this.get(curURL);
    }

    private URLEntity get(String curURL) {
        URLEntity urlEntity = null;

        // 开启内存存储，则从内存查找
        if (this.needCache) urlEntity = this.getByCache(curURL);

        return urlEntity;
    }

    private URLEntity getByCache(String curURL) {
        URLDao urlDao = this.urlDaoFactory.getURLDao(URLDaoEnum.CACHE);

        return urlDao.getByCurURL(curURL);
    }
}
