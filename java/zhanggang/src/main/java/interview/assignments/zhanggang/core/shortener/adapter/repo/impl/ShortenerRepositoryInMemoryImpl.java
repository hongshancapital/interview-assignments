package interview.assignments.zhanggang.core.shortener.adapter.repo.impl;

import interview.assignments.zhanggang.core.shortener.adapter.repo.ShortenerRepository;
import interview.assignments.zhanggang.core.shortener.model.Shortener;
import interview.assignments.zhanggang.support.lock.LockHandler;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Repository
public class ShortenerRepositoryInMemoryImpl implements ShortenerRepository {
    private final LockHandler lockHandler;
    private final Map<String, Shortener> idToShortener;
    private final Map<String, String> originalUrlToId;
    private final Queue<String> ids;

    public ShortenerRepositoryInMemoryImpl(LockHandler lockHandler) {
        this.lockHandler = lockHandler;
        idToShortener = new ConcurrentHashMap<>();
        originalUrlToId = new LinkedHashMap<>();
        ids = new ConcurrentLinkedQueue<>();
    }

    @Override
    public Mono<Shortener> isExist(String originalUrl) {
        return Mono.fromCallable(() ->
                lockHandler.read(originalUrl, () -> {
                    final String id = originalUrlToId.get(originalUrl);
                    if (id != null) {
                        return idToShortener.get(id);
                    }
                    return null;
                })
        );
    }

    @Override
    public Mono<Shortener> save(Shortener shortener) {
        return Mono.fromCallable(() ->
                lockHandler.write(shortener.getOriginalUrl(), () -> {
                    final String id = originalUrlToId.get(shortener.getOriginalUrl());
                    if (id != null) {
                        return idToShortener.get(id);
                    }
                    idToShortener.put(shortener.getId(), shortener);
                    originalUrlToId.put(shortener.getOriginalUrl(), shortener.getId());
                    ids.add(shortener.getId());
                    return shortener;
                })
        );
    }

    @Override
    public Mono<Shortener> findById(String id) {
        return Mono.fromCallable(() -> idToShortener.get(id));
    }
}
