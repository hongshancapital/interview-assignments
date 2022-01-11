package interview.assignments.zhanggang.core.shortener.adapter.repo.impl;

import interview.assignments.zhanggang.core.shortener.adapter.repo.ShortenerRepository;
import interview.assignments.zhanggang.core.shortener.model.Shortener;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ShortenerRepositoryInMemoryImpl implements ShortenerRepository {
    private final Map<String, Shortener> values;
    private final Map<String, String> urls;

    public ShortenerRepositoryInMemoryImpl() {
        values = new ConcurrentHashMap<>();
        urls = new ConcurrentHashMap<>();
    }

    @Override
    public Mono<Boolean> isExist(String url) {
        return Mono.fromCallable(() -> urls.containsKey(url));
    }

    @Override
    public Mono<Shortener> save(Shortener shortener) {
        return Mono.fromCallable(() -> {
            values.put(shortener.getId(), shortener);
            urls.put(shortener.getOriginalUrl(), shortener.getId());
            return shortener;
        });
    }

    @Override
    public Mono<Shortener> findById(String id) {
        return Mono.fromCallable(() -> values.get(id));
    }
}
