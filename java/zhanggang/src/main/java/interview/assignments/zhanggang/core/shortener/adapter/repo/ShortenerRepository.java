package interview.assignments.zhanggang.core.shortener.adapter.repo;

import interview.assignments.zhanggang.core.shortener.model.Shortener;
import reactor.core.publisher.Mono;

public interface ShortenerRepository {
    Mono<Boolean> isExist(String url);

    Mono<Shortener> save(Shortener shortener);

    Mono<Shortener> findById(String id);
}
