package interview.assignments.zhanggang.core.shortener.adapter.context;

import reactor.core.publisher.Mono;

public interface ShortIdContext {
    Mono<String> newShortenerId();
}
