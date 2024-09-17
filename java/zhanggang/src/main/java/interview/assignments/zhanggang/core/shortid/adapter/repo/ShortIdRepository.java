package interview.assignments.zhanggang.core.shortid.adapter.repo;

import interview.assignments.zhanggang.core.shortid.model.ShortId;
import reactor.core.publisher.Mono;

public interface ShortIdRepository {
    Mono<ShortId> newShortId();
}
