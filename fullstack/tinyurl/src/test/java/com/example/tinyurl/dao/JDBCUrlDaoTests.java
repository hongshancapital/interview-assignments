package com.example.tinyurl.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for JDBCUrlDao
 * @author hermitriver
 */
public class JDBCUrlDaoTests {
    private final static String TARGET_URL = "http://www.baidu.com";
    private final static String TINY_URL = "1";
    private final static Long VALID_ID = Long.valueOf(1);

    private static JDBCUrlDao dao;

    /** Init for all tests */
    @BeforeAll()
    public static void init() {
        dao = new JDBCUrlDao();
        dao.jdbcTemplate = mock(JdbcTemplate.class);
    }

    /** test getMaxId */
    @Test
    public void testGetMaxId() {
        // Valid max id
        given(dao.jdbcTemplate.queryForObject(any(), eq(Long.class))).willReturn(VALID_ID);
        Assertions.assertEquals(VALID_ID, dao.getMaxId());

        // throw EmptyResultDataAccessException
        given(dao.jdbcTemplate.queryForObject(any(), eq(Long.class))).willThrow(new EmptyResultDataAccessException(1));
        Assertions.assertNull(dao.getMaxId());

        // throw Exception
        given(dao.jdbcTemplate.queryForObject(any(), eq(Long.class))).willThrow(new NumberFormatException("Format Err"));
        Assertions.assertNull(dao.getMaxId());
    }

    /** test getTargetUrl */
    @Test
    public void testGetTargetUrl() {
        // Valid max target url
        given(dao.jdbcTemplate.queryForObject(any(), eq(new Object[] {TINY_URL}), eq(String.class))).willReturn(TARGET_URL);
        Assertions.assertEquals(TARGET_URL, dao.getTargetUrl(TINY_URL));

        // throw EmptyResultDataAccessException
        given(dao.jdbcTemplate.queryForObject(any(), eq(new Object[] {TINY_URL}), eq(String.class))).willThrow(new EmptyResultDataAccessException(1));
        Assertions.assertNull(dao.getTargetUrl(TINY_URL));

        // throw Exception
        given(dao.jdbcTemplate.queryForObject(any(), eq(new Object[] {TINY_URL}), eq(String.class))).willThrow(new NumberFormatException("Format Err"));
        Assertions.assertNull(dao.getTargetUrl(TINY_URL));
    }

    /** test save */
    @Test
    public void testSave() {
        // Save successfully
        given(dao.jdbcTemplate.update(any(), eq(VALID_ID), eq(TINY_URL), eq(TARGET_URL))).willReturn(1);
        Assertions.assertTrue(dao.save(TINY_URL, TARGET_URL));

        // Fail to save
        given(dao.jdbcTemplate.update(any(), eq(VALID_ID), eq(TINY_URL), eq(TARGET_URL))).willReturn(0);
        Assertions.assertFalse(dao.save(TINY_URL, TARGET_URL));

        // throw Exception
        given(dao.jdbcTemplate.update(any(), eq(VALID_ID), eq(TINY_URL), eq(TARGET_URL))).willThrow(new EmptyResultDataAccessException(1));
        Assertions.assertFalse(dao.save(TINY_URL, TARGET_URL));
    }
}
