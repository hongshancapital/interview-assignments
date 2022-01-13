package interview.assignments.zhanggang.core.shortener.adapter.repo.impl;

import interview.assignments.zhanggang.core.shortener.adapter.repo.ShortenerRepository;
import interview.assignments.zhanggang.core.shortener.model.Shortener;
import interview.assignments.zhanggang.support.lock.LockHandler;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        return Mono.fromCallable(() ->
                lockHandler.read(url, () -> {
                    final String id = urls.get(url);
                    if (id != null) {
                        return values.get(id);
                    }
                    return null;
                })
        );
    }

    @Override
    public Mono<Shortener> save(Shortener shortener) {
        return Mono.fromCallable(() ->
                lockHandler.write(shortener.getOriginalUrl(), () -> {
                    final String id = urls.get(shortener.getOriginalUrl());
                    if (id != null) {
                        return values.get(id);
                    }
                    values.put(shortener.getId(), shortener);
                    urls.put(shortener.getOriginalUrl(), shortener.getId());
                    return shortener;
                })
        );
    }

    @Override
    public Mono<Shortener> findById(String id) {
        return Mono.fromCallable(() -> values.get(id));
    }
}
