package interview.assignments.zhanggang.core.shortener.adapter.repo.impl;

import interview.assignments.zhanggang.config.exception.error.OriginalUrlAlreadyExistException;
import interview.assignments.zhanggang.core.shortener.model.Shortener;
import interview.assignments.zhanggang.support.lock.LockHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.util.concurrent.Callable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShortenerRepositoryInMemoryImplTest {
    @InjectMocks
    private ShortenerRepositoryInMemoryImpl shortenerRepositoryInMemory;
    @Mock
    private LockHandler lockHandler;

    private void mockReadLockHandler() {
        when(lockHandler.read(any(), any())).thenAnswer(invocation -> {
            final Callable<Mono<Shortener>> callable = invocation.getArgument(1);
            return callable.call();
        });
    }

    private void mockWriteLockHandler() {
        when(lockHandler.write(any(), any())).thenAnswer(invocation -> {
            final Callable<Mono<Shortener>> callable = invocation.getArgument(1);
            return callable.call();
        });
    }

    @Test
    void test_save_shortener() {
        mockWriteLockHandler();
        final Shortener shortener = new Shortener("a12a", "https://shortener-zg.com", Instant.now());
        final Mono<Shortener> save = shortenerRepositoryInMemory.save(shortener);

        StepVerifier.create(save)
                .expectNext(shortener)
                .verifyComplete();
    }

    @Test
    void test_save_shortener_with_an_exist_url() {
        mockWriteLockHandler();
        final Shortener shortener1 = new Shortener("a12a", "https://shortener-zg.com", Instant.now());
        shortenerRepositoryInMemory.save(shortener1).block();

        final Shortener shortener2 = new Shortener("1234", "https://shortener-zg.com", Instant.now());
        final Mono<Shortener> save = shortenerRepositoryInMemory.save(shortener2);

        StepVerifier.create(save)
                .verifyError(OriginalUrlAlreadyExistException.class);
    }

    @Test
    void test_find_shortener_by_a_valid_id() {
        mockWriteLockHandler();
        final Shortener shortener = new Shortener("a12a", "https://shortener-zg.com", Instant.now());
        shortenerRepositoryInMemory.save(shortener).block();

        final Mono<Shortener> byId = shortenerRepositoryInMemory.findById("a12a");
        StepVerifier.create(byId)
                .expectNext(shortener)
                .verifyComplete();
    }

    @Test
    void test_find_shortener_by_a_invalid_id() {
        final Mono<Shortener> byId = shortenerRepositoryInMemory.findById("99aa");
        StepVerifier.create(byId)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void test_an_exist_shortener() {
        mockWriteLockHandler();
        mockReadLockHandler();
        final Shortener shortener = new Shortener("a12a", "https://shortener-zg.com", Instant.now());
        shortenerRepositoryInMemory.save(shortener).block();

        final Mono<Boolean> isExist = shortenerRepositoryInMemory.isExist("https://shortener-zg.com");
        StepVerifier.create(isExist)
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void test_an_not_exist_shortener() {
        mockReadLockHandler();
        final Mono<Boolean> isExist = shortenerRepositoryInMemory.isExist("https://test-zg.com");
        StepVerifier.create(isExist)
                .expectNext(false)
                .verifyComplete();
    }
}