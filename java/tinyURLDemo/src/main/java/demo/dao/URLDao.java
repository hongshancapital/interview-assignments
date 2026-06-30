package demo.dao;

import demo.entity.URLEntity;

public interface URLDao {
    URLEntity getByOriURL(String oriURL) throws ClassCastException;

    URLEntity getByCurURL(String curURL) throws ClassCastException;

    boolean containByOriURL(String oriURL);

    boolean save(URLEntity urlEntity);
}
