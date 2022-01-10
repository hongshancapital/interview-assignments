package interview.assignments.zhanggang.core.shortcode.application;

import interview.assignments.zhanggang.core.shortcode.adapter.repo.ShortCodeRepository;
import interview.assignments.zhanggang.core.shortcode.model.ShortCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ShortCodeApplicationService {
    private final ShortCodeRepository shortCodeRepository;

    public Mono<ShortCode> getNewShortCode() {
        return shortCodeRepository.newShortCode();
    }
}
