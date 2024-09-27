package demo.dao;

import com.github.benmanes.caffeine.cache.Cache;
import demo.entity.URLEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 本机缓存方式 dao 层实现
 * 多节点部署或读写分离后，可能不再使用
 * 开启 springboot 的缓存配置，通过注释相关查询方法作为一级缓存
 * 多节点部署需注意请求的路由规则，尽量保证相同的短域名解析请求可以落在同一解析服务节点
 *
 * 本 demo 中，原始域名和域名公用一个缓存器，暂无拆分必要
 * 1. 二者不会出现字符串内容相同
 * 2. 需要同时存储对应信息，拆分后也不能节省空间
 */
@Component("URLCacheDao")
@Slf4j
public class URLCacheDao implements URLDao {
    private final Cache<String, Object> cache;

    public URLCacheDao(Cache<String, Object> cache) {
        this.cache = cache;
    }

    @Override
    public URLEntity getByOriURL(String oriURL) throws ClassCastException {
        return (URLEntity) this.cache.getIfPresent(oriURL);
    }

    @Override
    public URLEntity getByCurURL(String curURL) throws ClassCastException {
        return (URLEntity) this.cache.getIfPresent(curURL);
    }

    @Override
    public boolean containByOriURL(String oriURL) {
        return Objects.nonNull(this.getByOriURL(oriURL));
    }

    @Override
    public boolean save(URLEntity urlEntity) {
        try {
            this.cache.put(urlEntity.getOriURL(), urlEntity);
            this.cache.put(urlEntity.getCurURL(), urlEntity);
        } catch (Exception e) {
            log.error("短域名保存异常。 Entity:{}", urlEntity.toString());
            return false;
        }

        return true;
    }
}
