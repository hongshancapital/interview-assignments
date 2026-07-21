package com.sequoiacap.tinyurl.service.impl;

import com.sequoiacap.tinyurl.exception.BadParamException;
import com.sequoiacap.tinyurl.exception.DataDuplicationException;
import com.sequoiacap.tinyurl.exception.DataNotExistException;
import com.sequoiacap.tinyurl.exception.NotAcceptDataException;
import com.sequoiacap.tinyurl.repository.TinyUrlDao;
import com.sequoiacap.tinyurl.service.IdGenerator;
import com.sequoiacap.tinyurl.service.TinyUrlService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TinyUrlServiceImpl implements TinyUrlService {
    private static final int RETRY_SIZE = 10;

    private static final int SALT_SIZE = 10;

    private static final Logger LOGGER = LoggerFactory.getLogger(TinyUrlServiceImpl.class);

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private TinyUrlDao tinyUrlDao;

    @Override
    public String createTinyUrl(String url) {
        checkUrl(url);
        String salt = "";
        for (int i = 0; i < RETRY_SIZE; ++i) {
            String tinyUrl = idGenerator.nextId(url + salt);
            Optional<String> urlOptional = tinyUrlDao.queryUrl(tinyUrl);
            if (urlOptional.isPresent() && urlOptional.get().equals(url)) {
                return tinyUrl;
            }
            if (urlOptional.isEmpty()) {
                try {
                    tinyUrlDao.save(url, tinyUrl);
                    return tinyUrl;
                } catch (DataDuplicationException e) {
                    // 并发执行导致出现此异常
                    LOGGER.info("data duplicate. srcUrl: {}, tinyUrl: {}", url, tinyUrl);
                }
            }
            salt = RandomStringUtils.random(SALT_SIZE); // 生成短码已存在则加盐重新生成
        }
        // 多次加盐生成短码仍有重复则记录日志并报错
        LOGGER.error("create tiny url error. srcUrl: {}", url);
        throw new NotAcceptDataException();
    }

    @Override
    public String getUrl(String tinyUrl) throws BadParamException, DataNotExistException {
        idGenerator.checkId(tinyUrl);
        Optional<String> urlOptional = tinyUrlDao.queryUrl(tinyUrl);
        if (urlOptional.isEmpty()) {
            throw new DataNotExistException();
        }
        return urlOptional.get();
    }

    private void checkUrl(String url) {
        UrlValidator urlValidator = new UrlValidator();
        if (!urlValidator.isValid(url)) {
            throw new BadParamException();
        }
    }
}
