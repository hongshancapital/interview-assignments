package interview.assignments.zhanggang.core.shortener.application;

import interview.assignments.zhanggang.support.ReadWriteLockHandler;
import org.springframework.stereotype.Service;

@Service
public class ShortenerApplicationService {
    private final ReadWriteLockHandler readWriteLockHandler;

    public ShortenerApplicationService(ReadWriteLockHandler readWriteLockHandler) {
        this.readWriteLockHandler = readWriteLockHandler;
    }



}
