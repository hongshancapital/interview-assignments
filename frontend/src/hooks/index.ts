import { useRef } from 'react';

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
    useTimeLimt
}