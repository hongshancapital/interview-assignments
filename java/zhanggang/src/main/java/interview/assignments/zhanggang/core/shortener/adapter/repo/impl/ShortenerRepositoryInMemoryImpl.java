package interview.assignments.zhanggang.core.shortener.adapter.repo.impl;

import interview.assignments.zhanggang.config.exception.error.OriginalUrlAlreadyExistException;
import interview.assignments.zhanggang.core.shortener.adapter.repo.ShortenerRepository;
import interview.assignments.zhanggang.core.shortener.model.Shortener;
import interview.assignments.zhanggang.support.MD5Util;
import interview.assignments.zhanggang.support.lock.LockHandler;
import org.springframework.stereotype.Repository;
import org.springframework.util.ConcurrentReferenceHashMap;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

@Repository
public class ShortenerRepositoryInMemoryImpl implements ShortenerRepository {
    private final LockHandler lockHandler;
    private final Map<String, Shortener> values;
    private final Map<String, String> urls;

    public ShortenerRepositoryInMemoryImpl(LockHandler lockHandler) {
        this.lockHandler = lockHandler;
        values = new ConcurrentHashMap<>();
        urls = new LinkedHashMap<>();
        //TODO count find times
    }

    @Override
    public Mono<Shortener> isExist(String url) {
        return Mono.fromCallable(() -> {
            final String urlHash = MD5Util.md5(url);
//            urlHash.hashCode()/1000 TODO
            return lockHandler.read(urlHash, () -> {
                final String id = urls.get(url);
                if (id != null) {
                    return values.get(id);
                }
                return null;
            });
        });
    }

    @Override
    public Mono<Shortener> save(Shortener shortener) {
        return Mono.fromCallable(() -> {
            final String urlHash = MD5Util.md5(shortener.getOriginalUrl());
            return lockHandler.write(urlHash, () -> {
                if (urls.containsKey(shortener.getOriginalUrl())) {
                    throw new OriginalUrlAlreadyExistException(shortener.getOriginalUrl());
                }
                values.put(shortener.getId(), shortener);
                urls.put(shortener.getOriginalUrl(), shortener.getId());
                return shortener;
            });
        });
    }

    @Override
    public Mono<Shortener> findById(String id) {
        return Mono.fromCallable(() -> values.get(id));
    }
}
