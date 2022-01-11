package interview.assignments.zhanggang.core.shortener.adapter.repo.impl;

import interview.assignments.zhanggang.core.shortener.adapter.repo.ShortenerRepository;
import interview.assignments.zhanggang.core.shortener.model.Shortener;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ShortenerRepositoryInMemoryImpl implements ShortenerRepository {
    private final Map<Long, Shortener> values;
    private final Map<String, Long> urls;

    public ShortenerRepositoryInMemoryImpl() {
        values = new HashMap<>();
        urls = new HashMap<>();
    }

    @Override
    public Mono<Shortener> isExist(String url) {
        return Mono.fromCallable(() -> {
            final Long id = urls.get(url);
            if (id != null) {
                return values.get(id);
            } else {
                return null;
            }
        });
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
    public Mono<Shortener> findById(long id) {
        return Mono.fromCallable(() -> values.get(id));
    }
}
