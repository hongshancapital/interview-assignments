import { useEffect, useLayoutEffect, useRef, useState } from "react";

export default function useInterval(callback: () => void, delay: number) {
    const savedCallback = useRef(callback);
    const [paused, setPause] = useState(false);
    const timer = useRef<number>();
    // 每次 interval 的开启时间
    let startTime = useRef(0);
    // 暂停时已经消耗的时间
    let consumeTime = useRef(0);
    const [stop, setStop] = useState(false);

    useLayoutEffect(() => {
        savedCallback.current = callback
    }, [callback]);


    useEffect(() => {
        if (!delay && delay !== 0) {
            return;
        }
        if (stop) {
            clearInterval(timer.current);
            startTime.current = 0;
            consumeTime.current = 0;
        } else if (paused) {
            clearInterval(timer.current);
            consumeTime.current = consumeTime.current + Date.now() - startTime.current;
        } else if (consumeTime.current) {
            timer.current = window.setTimeout(() => {
                savedCallback.current();
                startTime.current = Date.now();
                consumeTime.current = 0;
                timer.current = window.setInterval(() => {
                    startTime.current = Date.now();
                    savedCallback.current();
                }, delay);
            }, delay - consumeTime.current);
            startTime.current = Date.now();
        } else {
            clearInterval(timer.current);
            timer.current = window.setInterval(() => {
                startTime.current = Date.now();
                savedCallback.current();
            }, delay);
            startTime.current = Date.now();
        }
        return () => {
            clearInterval(timer.current);
        };
    }, [delay, paused, stop]);


    return {
        pause: () => {
            setPause(true);
        },
        stop: () => {
            setStop(true);
        },
        resume: () => {
            setPause(false);
            setStop(false);
        },
        paused,
    }
}