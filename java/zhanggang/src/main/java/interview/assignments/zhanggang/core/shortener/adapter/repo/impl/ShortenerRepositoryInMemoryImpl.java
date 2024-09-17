package interview.assignments.zhanggang.core.shortener.adapter.repo.impl;

import interview.assignments.zhanggang.config.properties.ShortenerProperties;
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
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

@Repository
public class ShortenerRepositoryInMemoryImpl implements ShortenerRepository {
    private final Map<String, Shortener> idToShortener;
    private final Map<String, String> originalUrlToId;
    private final Queue<String> ids;
    private final Lock gcLock;

    private final LockHandler lockHandler;
    private final ShortenerProperties shortenerProperties;

    public ShortenerRepositoryInMemoryImpl(LockHandler lockHandler, ShortenerProperties shortenerProperties) {
        idToShortener = new ConcurrentHashMap<>();
        originalUrlToId = new LinkedHashMap<>();
        ids = new ConcurrentLinkedQueue<>();
        gcLock = new ReentrantLock();

        this.lockHandler = lockHandler;
        this.shortenerProperties = shortenerProperties;
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
                    if (idToShortener.size() >= shortenerProperties.getMaxStoreSize()) {
                        gc();
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

    void gc() {
        gcLock.lock();
        try {
            if (idToShortener.size() < shortenerProperties.getMaxStoreSize()) {
                return;
            }
            IntStream.range(0, (int) (shortenerProperties.getMaxStoreSize() * shortenerProperties.getGcRate())).forEach(i -> {
                final String id = ids.poll();
                final Shortener shortener = idToShortener.remove(id);
                originalUrlToId.remove(shortener.getOriginalUrl());
            });
        } finally {
            gcLock.unlock();
        }
    }
}
