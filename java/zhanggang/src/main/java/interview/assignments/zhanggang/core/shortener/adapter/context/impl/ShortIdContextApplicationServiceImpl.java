package interview.assignments.zhanggang.core.shortener.adapter.context.impl;

import interview.assignments.zhanggang.core.shortener.adapter.context.ShortIdContext;
import interview.assignments.zhanggang.core.shortid.application.ShortIdApplicationService;
import interview.assignments.zhanggang.core.shortid.model.ShortId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ShortIdContextApplicationServiceImpl implements ShortIdContext {
    private final ShortIdApplicationService shortIdApplicationService;

    @Override
    public Mono<String> newShortId() {
        return shortIdApplicationService.newShortId().map(ShortId::getValue);
    }
}
