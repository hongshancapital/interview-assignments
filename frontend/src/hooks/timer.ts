import {useCallback, useEffect, useRef} from 'react';

export function useInterval(func: () => void, interval: number) {
    const funcRef = useRef<() => void>();
    const timerRef = useRef<NodeJS.Timer>();

    const clear = useCallback(() => {
        if (timerRef.current) {
            clearInterval(timerRef.current);
        }
    }, []);

    useEffect(() => {
        funcRef.current = func;
    }, [func]);

    useEffect(() => {
        timerRef.current = setInterval(() => {
            if (funcRef.current) {
                funcRef.current();
            }
        }, interval);

        return clear;
    }, [interval]);

    return clear;
}
