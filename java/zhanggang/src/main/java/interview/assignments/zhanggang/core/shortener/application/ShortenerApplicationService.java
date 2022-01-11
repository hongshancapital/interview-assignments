package interview.assignments.zhanggang.core.shortener.application;

import interview.assignments.zhanggang.core.shortener.adapter.context.impl.ShortIdContextApplicationServiceImpl;
import interview.assignments.zhanggang.core.shortener.adapter.repo.ShortenerRepository;
import interview.assignments.zhanggang.core.shortener.model.Shortener;
import interview.assignments.zhanggang.support.LockHandler;
import interview.assignments.zhanggang.support.MD5Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ShortenerApplicationService {
    private final LockHandler lockHandler;
    private final ShortenerRepository shortenerRepository;
    private final ShortIdContextApplicationServiceImpl shortIdContext;

    public Mono<Shortener> createNewShortener(String url) {
        return lockHandler.lock(MD5Util.md5(url), () ->
                shortenerRepository.isExist(url)
                        .flatMap(isExist -> {
                            if (Boolean.TRUE.equals(isExist)) {
                                return Mono.error(new RuntimeException());
                            }
                            return shortIdContext.newShortenerId()
                                    .map(id -> new Shortener(id, url, Instant.now()))
                                    .flatMap(shortenerRepository::save);
                        })
        );
    }

    public Mono<Shortener> findById(String id) {
        return shortenerRepository.findById(id);
    }
}
