package com.shorturl.dao.impl;

import com.mysql.jdbc.Statement;
import com.shorturl.dao.ServerVersionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ServerVersionDaoImpl implements ServerVersionDao {

    /** 自增 id 步进值 */
    public static final Long AUTO_INCREMENT_STEP = 1000L;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Long insert() {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(conn -> conn.prepareStatement("INSERT INTO server_version VALUE()",
                Statement.RETURN_GENERATED_KEYS), keyHolder);

        return keyHolder.getKey().intValue() - 1L;
    }
}
