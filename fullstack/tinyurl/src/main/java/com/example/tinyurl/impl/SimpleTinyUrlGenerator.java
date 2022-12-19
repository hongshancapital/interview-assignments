package com.example.tinyurl.impl;

import com.example.tinyurl.dao.UrlDao;
import com.example.tinyurl.service.TinyUrlGenerator;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Tiny Url Generated sequentially in a single server node
 * @author hermitriver
 */
@Service
public class SimpleTinyUrlGenerator implements TinyUrlGenerator, InitializingBean {
    private long _cursor = -1;

    @Autowired
    UrlDao urlDao;

    /**
     * TinyUrl Generator Constructor
     */
    public SimpleTinyUrlGenerator() {}

    /** {@inheritDoc} */
    @Override
    public String generate(String targetUrl) {
        long value = getCursor();
        return SimpleTinyUrlHelper.map(value);
    }

    private synchronized long getCursor() {
        if (_cursor >= SimpleTinyUrlHelper.MAX)
            return -1;
        _cursor++;
        return _cursor;
    }

    /**
     * Run when spring initialize this bean (only once)
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (urlDao != null) {
            Long id = urlDao.getMaxId();
            if (id != null) {
                this._cursor = id.longValue();
            }
        }
    }
}
