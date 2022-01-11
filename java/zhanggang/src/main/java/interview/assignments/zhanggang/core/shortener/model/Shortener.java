package interview.assignments.zhanggang.core.shortener.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;

@EqualsAndHashCode
@RequiredArgsConstructor
@Getter
public class Shortener {
    private final long id;
    private final String originalUrl;
    private final Instant createAt;
}
