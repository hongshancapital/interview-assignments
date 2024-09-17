package interview.assignments.zhanggang.core.shortener.adapter.repo;

import interview.assignments.zhanggang.core.shortener.model.Shortener;
import reactor.core.publisher.Mono;

public interface ShortenerRepository {
    Mono<Shortener> isExist(String originalUrl);

    Mono<Shortener> save(Shortener shortener);

    Mono<Shortener> findById(String id);
}
