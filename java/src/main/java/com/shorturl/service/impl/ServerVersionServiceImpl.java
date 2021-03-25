package com.shorturl.service.impl;

import com.shorturl.cache.ServerVersionCache;
import com.shorturl.dao.ServerVersionDao;
import com.shorturl.service.ServerVersionService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by ruohanpan on 21/3/23.
 */
@Service
public class ServerVersionServiceImpl implements ServerVersionService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final Long MAX_VERSION = 999L;

    public static final String LOCK_KEY = "version_in_use_lock";

    @Autowired
    private ServerVersionDao serverVersionDao;

    @Autowired
    private ServerVersionCache serverVersionCache;

    @Autowired
    private RedissonClient redissonClient;

    private String uuid;

    private volatile Long version;

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        logger.info("get server uuid is {}", uuid);
    }

    private Long getVersion() throws Exception {

        RLock lock = redissonClient.getLock(LOCK_KEY);
        if (!lock.tryLock(3, 30, TimeUnit.SECONDS)) {
            throw new RuntimeException();
        }

        try {
            version = serverVersionDao.insert();
            if (version <= MAX_VERSION) {
                serverVersionCache.addInUseCache(version);
                return version;
            }

            Set<String> inUseVersion = serverVersionCache.readInUseCache();
            for (Long i = 0L; i < MAX_VERSION; i++) {
                if (inUseVersion.contains(i.toString())) {
                    continue;
                }
                serverVersionCache.addInUseCache(i);
                return i;
            }
        } finally {
            lock.unlock();
        }

        throw new RuntimeException("no available initial code");
    }

    public Long updateVersion() throws Exception {

        version = tryGetVersion();
        logger.info("get updated version {}", version);
        return version;
    }

    private Long tryGetVersion() throws Exception {

        int retryCount = 5;
        while (retryCount > 0) {
            try {
                return getVersion();
            } catch (Exception e) {
                continue;
            }
        }

        throw new RuntimeException("tryGetVersion got error");
    }
}
