import { useEffect, useMemo, useRef } from 'react';
import useEvent from './useEvent';

type CallBackFn = () => void;

const useInterval = (interval: number, fn: CallBackFn) => {
    const memoiedFn = useEvent(fn);
    const timerRef = useRef<null | NodeJS.Timeout>(null);
    const setupTimer = useEvent(() => {
        timerRef.current && clearTimeout(timerRef.current);
        timerRef.current = setTimeout(() => {
            memoiedFn();
            // eslint-disable-next-line @typescript-eslint/no-unused-vars
            setupTimer();
        }, interval);
    });

    useEffect(() => {
        setupTimer();

        return () => {
            timerRef.current && clearTimeout(timerRef.current);
            timerRef.current = null;
        };
    }, [interval, memoiedFn, setupTimer]);

    return useMemo(
        () =>
            [
                () => {
                    timerRef.current && clearTimeout(timerRef.current);
                    timerRef.current = null;
                },
                () => {
                    setupTimer();
                },
            ] as const,
        [setupTimer]
    );
};

export default useInterval;
