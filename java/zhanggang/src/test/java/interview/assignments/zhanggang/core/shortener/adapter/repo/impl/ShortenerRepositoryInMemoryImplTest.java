package interview.assignments.zhanggang.core.shortener.adapter.repo.impl;

import interview.assignments.zhanggang.config.properties.ShortenerProperties;
import interview.assignments.zhanggang.core.shortener.model.Shortener;
import interview.assignments.zhanggang.support.lock.LockHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.Callable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShortenerRepositoryInMemoryImplTest {
    @InjectMocks
    private ShortenerRepositoryInMemoryImpl shortenerRepositoryInMemory;
    @Mock
    private LockHandler lockHandler;
    @Mock
    private ShortenerProperties shortenerProperties;

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
        when(shortenerProperties.getMaxStoreSize()).thenReturn(20);
        final Shortener shortener = new Shortener("a12a", "https://shortener-zg.com");
        final Mono<Shortener> save = shortenerRepositoryInMemory.save(shortener);

        StepVerifier.create(save)
                .expectNext(shortener)
                .verifyComplete();
    }

    @Test
    void test_save_shortener_with_an_exist_url() {
        mockWriteLockHandler();
        when(shortenerProperties.getMaxStoreSize()).thenReturn(20);
        final Shortener shortener1 = new Shortener("a12a", "https://shortener-zg.com");
        shortenerRepositoryInMemory.save(shortener1).block();

        final Shortener shortener2 = new Shortener("1234", "https://shortener-zg.com");
        final Mono<Shortener> save = shortenerRepositoryInMemory.save(shortener2);

        StepVerifier.create(save)
                .expectNext(shortener1)
                .verifyComplete();
    }

    @Test
    void test_find_shortener_by_a_valid_id() {
        mockWriteLockHandler();
        when(shortenerProperties.getMaxStoreSize()).thenReturn(20);
        final Shortener shortener = new Shortener("a12a", "https://shortener-zg.com");
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
    void test_is_exist_shortener() {
        mockWriteLockHandler();
        mockReadLockHandler();
        when(shortenerProperties.getMaxStoreSize()).thenReturn(20);
        final Shortener shortener = new Shortener("a12a", "https://shortener-zg.com");
        shortenerRepositoryInMemory.save(shortener).block();

        final Mono<Shortener> isExist = shortenerRepositoryInMemory.isExist("https://shortener-zg.com");
        StepVerifier.create(isExist)
                .expectNext(shortener)
                .verifyComplete();
    }

    @Test
    void test_is_not_exist_shortener() {
        mockReadLockHandler();
        final Mono<Shortener> isExist = shortenerRepositoryInMemory.isExist("https://test-zg.com");
        StepVerifier.create(isExist)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void test_save_shortener_with_gc() {
        mockWriteLockHandler();
        when(shortenerProperties.getMaxStoreSize()).thenReturn(2);
        when(shortenerProperties.getGcRate()).thenReturn(0.5f);
        shortenerRepositoryInMemory.save(new Shortener("1", "https://shortener-zg1.com")).block();
        shortenerRepositoryInMemory.save(new Shortener("2", "https://shortener-zg2.com")).block();

        shortenerRepositoryInMemory.save(new Shortener("3", "https://shortener-zg3.com")).block();


        final Mono<Shortener> s1 = shortenerRepositoryInMemory.findById("1");
        final Mono<Shortener> s2 = shortenerRepositoryInMemory.findById("2");
        final Mono<Shortener> s3 = shortenerRepositoryInMemory.findById("3");

        StepVerifier.create(s1).expectNextCount(0).verifyComplete();
        StepVerifier.create(s2).expectNextCount(1).verifyComplete();
        StepVerifier.create(s3).expectNextCount(1).verifyComplete();
    }

    @Test
    void test_gc_ignore() {
        mockWriteLockHandler();
        when(shortenerProperties.getMaxStoreSize()).thenReturn(3);
        shortenerRepositoryInMemory.save(new Shortener("1", "https://shortener-zg1.com")).block();
        shortenerRepositoryInMemory.save(new Shortener("2", "https://shortener-zg2.com")).block();

        shortenerRepositoryInMemory.gc();

        final Mono<Shortener> s1 = shortenerRepositoryInMemory.findById("1");
        final Mono<Shortener> s2 = shortenerRepositoryInMemory.findById("2");

        StepVerifier.create(s1).expectNextCount(1).verifyComplete();
        StepVerifier.create(s2).expectNextCount(1).verifyComplete();
    }
}