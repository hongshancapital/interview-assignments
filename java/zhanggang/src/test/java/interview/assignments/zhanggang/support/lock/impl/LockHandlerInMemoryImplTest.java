package interview.assignments.zhanggang.support.lock.impl;


import interview.assignments.zhanggang.config.exception.base.SystemException;
import interview.assignments.zhanggang.config.exception.error.LockTimeoutException;
import interview.assignments.zhanggang.config.properties.ShortenerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LockHandlerInMemoryImplTest {
    @InjectMocks
    private LockHandlerInMemoryImpl lockHandler;
    @Mock
    private ShortenerConfig shortenerConfig;
    @Mock
    private ShortenerConfig.LockConfig lockConfig;

    @BeforeEach
    void setup() {
        when(shortenerConfig.getLockConfig()).thenReturn(lockConfig);
        when(lockConfig.getTimeunit()).thenReturn(TimeUnit.SECONDS);
        when(lockConfig.getMaxPoolSize()).thenReturn(5);
    }

    @Test
    void test_read_lock_call_conflict_and_no_time_out() {
        when(lockConfig.getTimeout()).thenReturn(10L);

        final String lockId = "1";
        final AtomicInteger counter = new AtomicInteger(0);

        CompletableFuture.allOf(
                CompletableFuture.runAsync(() ->
                        lockHandler.read(lockId, () -> {
                            counter.incrementAndGet();
                            return Mono.delay(Duration.ofMillis(100)).block();
                        })
                ),
                CompletableFuture.runAsync(() ->
                        lockHandler.read(lockId, () -> {
                            counter.incrementAndGet();
                            return Mono.delay(Duration.ofMillis(100)).block();
                        })
                )
        ).join();

        assertThat(counter.get()).isEqualTo(2);
    }

    @Test
    void test_read_lock_call_conflict_and_time_out() {
        when(lockConfig.getTimeout()).thenReturn(0L);

        final String lockId = "1";
        final AtomicInteger counter = new AtomicInteger(0);

        CompletableFuture.allOf(
                CompletableFuture.runAsync(() ->
                        lockHandler.read(lockId, () -> {
                            counter.incrementAndGet();
                            return Mono.delay(Duration.ofMillis(100)).block();
                        })
                ),
                CompletableFuture.runAsync(() ->
                        lockHandler.read(lockId, () -> {
                            counter.incrementAndGet();
                            return Mono.delay(Duration.ofMillis(100)).block();
                        })
                )
        ).join();

        assertThat(counter.get()).isEqualTo(2);
    }

    @Test
    void test_read_write_call_conflict_and_no_time_out() {
        when(lockConfig.getTimeout()).thenReturn(10L);

        final String lockId = "1";
        final AtomicInteger counter = new AtomicInteger(0);

        CompletableFuture.allOf(
                CompletableFuture.runAsync(() ->
                        lockHandler.read(lockId, () -> {
                            counter.incrementAndGet();
                            return Mono.delay(Duration.ofMillis(100)).block();
                        })
                ),
                CompletableFuture.runAsync(() ->
                        lockHandler.write(lockId, () -> {
                            counter.incrementAndGet();
                            return Mono.delay(Duration.ofMillis(100)).block();
                        })
                )
        ).join();

        assertThat(counter.get()).isEqualTo(2);
    }

    @Test
    void test_read_write_call_conflict_and_time_out() {
        when(lockConfig.getTimeout()).thenReturn(0L);

        final String lockId = "1";
        final AtomicInteger counter = new AtomicInteger(0);

        final CompletableFuture<Void> futures = CompletableFuture.allOf(
                CompletableFuture.runAsync(() ->
                        lockHandler.read(lockId, () -> {
                            counter.incrementAndGet();
                            return Mono.delay(Duration.ofMillis(100)).block();
                        })
                ),
                CompletableFuture.runAsync(() ->
                        lockHandler.write(lockId, () -> {
                            counter.incrementAndGet();
                            return Mono.delay(Duration.ofMillis(100)).block();
                        })
                )
        );

        assertThatThrownBy(futures::join).hasCauseInstanceOf(LockTimeoutException.class);
        assertThat(counter.get()).isEqualTo(1);
    }

    @Test
    void test_write_write_call_conflict_and_no_time_out() {
        when(lockConfig.getTimeout()).thenReturn(10L);

        final String lockId = "1";
        final AtomicInteger counter = new AtomicInteger(0);

        CompletableFuture.allOf(
                CompletableFuture.runAsync(() ->
                        lockHandler.write(lockId, () -> {
                            counter.incrementAndGet();
                            return Mono.delay(Duration.ofMillis(100)).block();
                        })
                ),
                CompletableFuture.runAsync(() ->
                        lockHandler.write(lockId, () -> {
                            counter.incrementAndGet();
                            return Mono.delay(Duration.ofMillis(100)).block();
                        })
                )
        ).join();

        assertThat(counter.get()).isEqualTo(2);
    }

    @Test
    void test_write_write_call_conflict_and_time_out() {
        when(lockConfig.getTimeout()).thenReturn(0L);

        final String lockId = "1";
        final AtomicInteger counter = new AtomicInteger(0);

        final CompletableFuture<Void> futures = CompletableFuture.allOf(
                CompletableFuture.runAsync(() ->
                        lockHandler.write(lockId, () -> {
                            counter.incrementAndGet();
                            return Mono.delay(Duration.ofMillis(100)).block();
                        })
                ),
                CompletableFuture.runAsync(() ->
                        lockHandler.write(lockId, () -> {
                            counter.incrementAndGet();
                            return Mono.delay(Duration.ofMillis(100)).block();
                        })
                )
        );

        assertThatThrownBy(futures::join).hasCauseInstanceOf(LockTimeoutException.class);
        assertThat(counter.get()).isEqualTo(1);
    }

    @Test
    void test_lock_success_but_call_failed() {
        when(lockConfig.getTimeout()).thenReturn(10L);

        final AtomicInteger counter = new AtomicInteger(0);

        final CompletableFuture<Void> futures = CompletableFuture.allOf(
                CompletableFuture.runAsync(() -> lockHandler.write("1", () -> Integer.parseInt("gg"))),
                CompletableFuture.runAsync(() -> lockHandler.write("1", counter::incrementAndGet))
        );

        assertThatThrownBy(futures::join).hasCauseInstanceOf(SystemException.class);
        assertThat(counter.get()).isEqualTo(1);
    }

    @Test
    void get_lock() {
        final AtomicInteger counter = new AtomicInteger(0);
        final CompletableFuture<Void>[] completableFutures = new CompletableFuture[100];
        for (int i = 0; i < 100; i++) {
            completableFutures[i] = CompletableFuture.runAsync(() ->
                    lockHandler.write("1", counter::incrementAndGet)
            );
        }
        CompletableFuture.allOf(completableFutures).join();
        assertThat(counter.get()).isEqualTo(100);
    }
}