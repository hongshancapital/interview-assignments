package com.sequoiacap.tinyurl.repository;

import com.sequoiacap.tinyurl.exception.DataDuplicationException;

import java.util.Optional;

public interface TinyUrlDao {

    void save(String url, String tinyUrl) throws DataDuplicationException;

    Optional<String> queryUrl(String tinyUrl);

}
