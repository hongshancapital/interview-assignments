import { useEffect, useLayoutEffect, useRef, useState } from "react";

export default function useInterval(callback: () => void, delay: number) {
    const savedCallback = useRef(callback);
    const [paused, setPause] = useState(false);
    const timer = useRef<number>();
    let startTime = useRef(0);
    let consumeTime = useRef(0);

    useLayoutEffect(() => {
        savedCallback.current = callback
    }, [callback])


    useEffect(() => {
        if (!delay && delay !== 0) {
            return;
        }
        if (paused) {
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
            timer.current = window.setInterval(() => {
                startTime.current = Date.now();
                savedCallback.current();
            }, delay);
            startTime.current = Date.now();
        }
        return () => {
            clearInterval(timer.current)
        };
    }, [delay, paused]);


    return {
        pause: () => {
            setPause(true);
        },
        resume: () => {
            setPause(false);
        },
        paused,
    }
}