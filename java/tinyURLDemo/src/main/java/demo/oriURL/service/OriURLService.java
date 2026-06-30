package demo.oriURL.service;

import demo.dao.URLDao;
import demo.dao.URLDaoEnum;
import demo.dao.URLDaoFactory;
import demo.entity.URLEntity;
import demo.strategy.URLTransStrategy;
import demo.strategy.URLTransStrategyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class OriURLService implements IOriURLService {
    private final URLDaoFactory urlDaoFactory;
    private final URLTransStrategyFactory urlTransStrategyFactory;

    @Autowired
    public OriURLService(URLDaoFactory urlDaoFactory, URLTransStrategyFactory urlTransStrategyFactory) {
        this.urlDaoFactory = urlDaoFactory;
        this.urlTransStrategyFactory = urlTransStrategyFactory;
    }

    // redis/mysql 变量考虑后续扩展用
    @Value("${url.dao.cache:true}")
    private boolean needCache;
    @Value("${url.dao.redis:false}")
    private boolean needRedis;
    @Value("${url.dao.mysql:true}")
    private boolean needMysql;

    @Value("${url.trans.strategy:Base62URLTransStrategy}")
    private String defaultStrategy;
    @Value("${url.tiny.prefix:https://t.cn/}")
    private String prefix;

    /**
     * 连接转换方法
     * @param oriURL 原链接
     * @return 短链接对象
     */
    @Override
    public URLEntity trans(String oriURL) {
        // 如已生成短链接，则返回已有结果
        URLEntity urlEntity = this.get(oriURL);
        if (Objects.nonNull(urlEntity)) return urlEntity;

        // 生成新的短链接
        urlEntity = this.genURLEntity(oriURL);
        if (Objects.isNull(urlEntity)) return null;

        // 存储短链接对象
        return this.save(urlEntity);
    }

    /**
     * 查询方法，考虑后续扩展
     * @param oriURL 原链接
     * @return 短链接对象
     */
    private URLEntity get(String oriURL) {
        URLEntity urlEntity = null;

        if (this.needCache) urlEntity = this.getByCache(oriURL);

        return urlEntity;
    }

    /**
     * 内存查询方法
     * @param oriURL 原链接
     * @return 短链接对象
     */
    private URLEntity getByCache(String oriURL) {
        URLDao urlDao = this.urlDaoFactory.getURLDao(URLDaoEnum.CACHE);

        return urlDao.getByOriURL(oriURL);
    }

    /**
     * 生成短链接对象
     * @param oriURL 原链接
     * @return 短链接对象
     */
    private URLEntity genURLEntity(String oriURL) {
        int id = URLIdGenerator.getId();

        URLTransStrategy strategy = this.urlTransStrategyFactory.getURLTransStrategy(this.defaultStrategy);
        if(Objects.isNull(strategy)) return null;

        String tinyURL = strategy.encode(id);
        if (tinyURL.length() > 8) return null;

        String curURL = this.prefix.concat(tinyURL);

        return URLEntity.builder()
                .id(id)
                .oriURL(oriURL)
                .curURL(curURL)
                .createTimeStamp(System.currentTimeMillis())
                .build();
    }

    /**
     * 保存短链接对象，考虑后续扩展
     * @param urlEntity 短链接对象
     * @return 短链接对象
     */
    private URLEntity save(URLEntity urlEntity) {
        if (this.needCache) urlEntity = this.saveByCache(urlEntity);

        return urlEntity;
    }

    /**
     * 保存短链接对象至内存
     * @param urlEntity 短链接对象
     * @return 短链接对象
     */
    private URLEntity saveByCache(URLEntity urlEntity) {
        synchronized (this) {
            URLDao urlDao = this.urlDaoFactory.getURLDao(URLDaoEnum.CACHE);

            if (urlDao.containByOriURL(urlEntity.getOriURL())) {
                urlEntity = urlDao.getByOriURL(urlEntity.getOriURL());
            } else {
                urlDao.save(urlEntity);
            }
        }

        return urlEntity;
    }
}
