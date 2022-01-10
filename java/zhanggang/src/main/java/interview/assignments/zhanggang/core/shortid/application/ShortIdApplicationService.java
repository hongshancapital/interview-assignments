package interview.assignments.zhanggang.core.shortid.application;

import interview.assignments.zhanggang.core.shortid.adapter.repo.ShortIdRepository;
import interview.assignments.zhanggang.core.shortid.model.ShortId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ShortIdApplicationService {
    private final ShortIdRepository shortIdRepository;

    public Mono<ShortId> newShortId() {
        return shortIdRepository.newShortId();
    }
}
