package interview.assignments.zhanggang.core.shortid.application;

import interview.assignments.zhanggang.config.exception.error.ShortIdMaximumLimitException;
import interview.assignments.zhanggang.config.properties.ShortenerProperties;
import interview.assignments.zhanggang.core.shortid.adapter.repo.ShortIdRepository;
import interview.assignments.zhanggang.core.shortid.model.ShortId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ShortIdApplicationService {
    private final ShortIdRepository shortIdRepository;
    private final ShortenerProperties shortenerProperties;


    public Mono<ShortId> newShortId() {
        return shortIdRepository.newShortId()
                .filter(shortId -> shortId.validate(shortenerProperties))
                .switchIfEmpty(Mono.error(new ShortIdMaximumLimitException()));
    }
}
