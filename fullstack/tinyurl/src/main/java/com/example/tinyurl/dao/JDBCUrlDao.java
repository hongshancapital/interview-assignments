package com.example.tinyurl.dao;

import com.example.tinyurl.impl.SimpleTinyUrlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * URL Data Access Object implementation via JDBC Template
 * @author hermitriver
 */
@Repository
public class JDBCUrlDao implements UrlDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    /** {@inheritDoc} */
    @Override
    public Long getMaxId() {
        String sql = "SELECT id FROM t_url ORDER BY id DESC LIMIT 1";
        Long id = null;
        try {
            id = this.jdbcTemplate.queryForObject(sql, Long.class);
        } catch (EmptyResultDataAccessException emptyException) {
            return null;
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
        return id;
    }

    /** {@inheritDoc} */
    @Override
    public String getTargetUrl(String tinyUrl) {
        String sql = "SELECT targetUrl FROM t_url WHERE tinyUrl = ?";
        String targetUrl = null;
        try {
            targetUrl = this.jdbcTemplate.queryForObject(sql, new Object[] {tinyUrl}, String.class);
        } catch (EmptyResultDataAccessException emptyException) {
            return null;
        } catch (Exception e) {
            System.err.println(e);
            return null;
        }
        return targetUrl;
    }

    /** {@inheritDoc} */
    @Override
    public boolean save(String tinyUrl, String targetUrl) {
        String sql = "INSERT INTO t_url(id, tinyurl, targeturl) VALUES (?, ?,?)";
        int count = 0;
        long id = SimpleTinyUrlHelper.map(tinyUrl);
        try {
            count = this.jdbcTemplate.update(sql, id, tinyUrl, targetUrl);
        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
        return count == 1;
    }
}
