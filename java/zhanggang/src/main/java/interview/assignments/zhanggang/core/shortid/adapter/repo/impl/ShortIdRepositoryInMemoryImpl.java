package interview.assignments.zhanggang.core.shortid.adapter.repo.impl;

import interview.assignments.zhanggang.core.shortid.adapter.repo.ShortIdRepository;
import interview.assignments.zhanggang.core.shortid.model.ShortId;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ShortIdRepositoryInMemoryImpl implements ShortIdRepository {
    private static final long INITIAL_VALUE = 1;
    private final AtomicLong seeds;

    public ShortIdRepositoryInMemoryImpl() {
        seeds = new AtomicLong(INITIAL_VALUE);
    }

    @Override
    public Mono<ShortId> newShortId() {
        return Mono.fromCallable(() -> new ShortId(seeds.getAndIncrement()));
    }
}
