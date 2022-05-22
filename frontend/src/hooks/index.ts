import { useRef } from 'react';

// 可取消的延时任务
const useDelayTask = () => {
    const timer = useRef<null | NodeJS.Timeout>(null);
    const newDelayTask = (cb: Function, timeout: number) => {
        timer.current = setTimeout(() => {
            cb();
            timer.current = null;
        }, timeout);
    }
    const cancelDelayTask = () => {
        if (timer.current) {
            clearTimeout(timer.current);
            timer.current = null;
        }
    }
    return {
        newDelayTask,
        cancelDelayTask
    }
}

// 锁
const useLock = (defaultValue: boolean) => {
    const isLockAnimation = useRef(defaultValue || false);  // 是否停止了动画
    const isLock = () => {
        return isLockAnimation.current;
    }
    const lock = () => {
        isLockAnimation.current = true;
    }
    const unLock = () => {
        isLockAnimation.current = false;
    }
    return {
        isLock,
        lock,
        unLock
    }
}

// 限时
const useTimeLimt = (limit: number) => {
    const cache = useRef({ limit, start: 0, end: 0 });
    const start = () => {
        cache.current.start = Date.now();
    }
    const stop = () => {
        cache.current.end = 0;
    }
    const reset = () => {
        cache.current.start = 0;
    }
    const getRunTime = () => {
        const { start } = cache.current;
        return Date.now() - start;
    }
    const isTimeout = () => {
        return getRunTime() >= cache.current.limit
    }
    return {
        start,
        stop,
        getRunTime,
        reset,
        isTimeout
    }
}

export {
    useDelayTask,
    useLock,
    useTimeLimt
}