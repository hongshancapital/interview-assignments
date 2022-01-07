package org.domain.component;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.stereotype.Component;

@Component
public class RateLimiterComponent {
    private RateLimiter rateLimiter = RateLimiter.create(5);

    public boolean tryAcquire() {
        return rateLimiter.tryAcquire();
    }
}
