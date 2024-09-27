package demo.dao;

import demo.entity.URLEntity;
import org.springframework.stereotype.Component;

/**
 * 使用 mysql 关系型数据库存取短域名，后续扩展用
 */
@Component("URLMysqlDao")
// 暂未使用，保证覆盖率先注释掉
public class URLMysqlDao {//implements URLDao {
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
