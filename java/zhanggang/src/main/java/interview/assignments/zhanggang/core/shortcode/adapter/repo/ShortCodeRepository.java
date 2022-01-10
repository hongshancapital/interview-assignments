package interview.assignments.zhanggang.core.shortcode.adapter.repo;

import interview.assignments.zhanggang.core.shortcode.model.ShortCode;
import reactor.core.publisher.Mono;

public interface ShortCodeRepository {
    Mono<ShortCode> newShortCode();
}
