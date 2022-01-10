package interview.assignments.zhanggang.core.shortcode.adapter.repo.impl;

import interview.assignments.zhanggang.config.properties.ShortCodeProperties;
import interview.assignments.zhanggang.core.shortcode.adapter.repo.ShortCodeRepository;
import interview.assignments.zhanggang.core.shortcode.model.ShortCode;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ShortCodeRepositoryInMemoryImpl implements ShortCodeRepository {
    private final ShortCodeProperties shortCodeProperties;
    private final AtomicLong seed;

    public ShortCodeRepositoryInMemoryImpl(ShortCodeProperties shortCodeProperties) {
        this.shortCodeProperties = shortCodeProperties;
        seed = new AtomicLong(1);
    }


    @Override
    public Mono<ShortCode> newShortCode() {
        return Mono.fromCallable(() -> new ShortCode(seed.getAndIncrement(), shortCodeProperties));
    }
}
