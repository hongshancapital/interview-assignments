package demo.dao;

import demo.entity.URLEntity;
import org.springframework.stereotype.Component;

/**
 * 使用 redis 存取短域名，后续扩展用
 */
@Component("URLRedisDao")
// 暂未使用，保证覆盖率先注释掉
public class URLRedisDao {//implements URLDao {
//    @Override
//    public URLEntity getByOriURL(String oriURL) throws ClassCastException {
//        return null;
//    }
//
//    @Override
//    public URLEntity getByCurURL(String curURL) throws ClassCastException {
//        return null;
//    }
//
//    @Override
//    public boolean containByOriURL(String oriURL) {
//        return false;
//    }
//
//    @Override
//    public boolean save(URLEntity urlEntity) {
//        return false;
//    }
}
