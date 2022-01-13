package interview.assignments.zhanggang.core.shortener.application;

import interview.assignments.zhanggang.config.exception.error.ShortenerNotFoundException;
import interview.assignments.zhanggang.config.properties.ShortenerProperties;
import interview.assignments.zhanggang.core.shortener.adapter.context.ShortIdContext;
import interview.assignments.zhanggang.core.shortener.adapter.repo.ShortenerRepository;
import interview.assignments.zhanggang.core.shortener.model.Shortener;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ShortenerApplicationService {
    private final ShortenerRepository shortenerRepository;
    private final ShortIdContext shortIdContext;
    private final ShortenerProperties shortenerProperties;

    public Mono<String> getShortUrl(String originalUrl) {
        return shortenerRepository.isExist(originalUrl)
                .switchIfEmpty(
                        Mono.defer(() -> shortIdContext.newShortId()
                                .map(id -> new Shortener(id, originalUrl))
                                .flatMap(shortenerRepository::save)
                        )
                )
                .map(shortener -> shortener.getShortUrl(shortenerProperties.getShortUrlHost()));
    }

    public Mono<String> getOriginalUrl(String shortUrl) {
        final String id = Shortener.parseId(shortUrl);
        return shortenerRepository.findById(id)
                .map(Shortener::getOriginalUrl)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new ShortenerNotFoundException(id))));
    }
}
