package com.shorturl.dao.impl;

import com.mysql.jdbc.Statement;
import com.shorturl.dao.ServerVersionDao;
import com.shorturl.dao.UrlMappingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;

@Repository
public class UrlMappingDaoImpl implements UrlMappingDao {

    private static final String URL_MAPPING_TABLE_PREFIX = "url_mapping_";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void create(Long intialCode) {
        StringBuilder sql = new StringBuilder();
        sql.append(String.format("CREATE TABLE %s%d", URL_MAPPING_TABLE_PREFIX, intialCode));
        sql.append("(");
        sql.append("id BIGINT AUTO_INCREMENT PRIMARY KEY,");
        sql.append("url VARCHAR(255)");
        sql.append(String.format(") ENGINE = InnoDB, AUTO_INCREMENT = %d", intialCode));

        jdbcTemplate.update(sql.toString());
    }

    public Long insert(Long version, String url) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                conn -> {
                    PreparedStatement statement = conn.prepareStatement(
                            String.format("INSERT INTO %s(url) VALUES(?)", getTableName(version)),
                            Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, url);
                    return statement;
                }, keyHolder);

        return calculatePhysicalCode(version, keyHolder.getKey().longValue());
    }

    public void delete(Long code) {
        long version = calculateVersion(code);
        long logicCode = calculateLogicCode(code);
        jdbcTemplate.update(String.format("DELETE FROM %s WHERE `id`=%d", getTableName(version), logicCode));
    }

    public String queryUrl(Long code) {
        Long version = calculateVersion(code);
        Long logicCode = calculateLogicCode(code);
        String url = jdbcTemplate.queryForObject(
                String.format("SELECT `url` FROM %s WHERE `id`=?", getTableName(version)),
                new Object[]{logicCode},
                (rs, rowNum) -> rs.getString("url"));

        return url;
    }


    private String getTableName(Long version) {
        return String.format("%s%d", URL_MAPPING_TABLE_PREFIX, version);
    }

    private Long calculatePhysicalCode(Long version, Long logicCode) {
        return (logicCode - version) * ServerVersionDao.AUTO_INCREMENT_STEP + version;
    }

    private Long calculateLogicCode(Long physicalCode) {
        Long version = calculateVersion(physicalCode);
        return physicalCode / ServerVersionDao.AUTO_INCREMENT_STEP + version;
    }

    private Long calculateVersion(Long physicalCode) {
        return physicalCode % ServerVersionDao.AUTO_INCREMENT_STEP;
    }
}
