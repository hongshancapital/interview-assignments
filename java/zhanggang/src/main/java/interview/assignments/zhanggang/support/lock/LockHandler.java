package interview.assignments.zhanggang.support.lock;

import java.util.concurrent.Callable;

public interface LockHandler {
    <T> T read(String lockId, Callable<? extends T> callable);

    <T> T write(String lockId, Callable<? extends T> callable);
}
