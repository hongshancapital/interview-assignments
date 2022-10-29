import { useEffect, useLayoutEffect, useRef, useState } from "react";

export default function useInterval(callback: () => void, delay: number) {
    const savedCallback = useRef(callback);
    const [paused, setPause] = useState(false);
    const timer = useRef<number>();
    let startTime = useRef(0);
    let consumeTime = useRef(0);
    const [stop, setStop] = useState(false);

    useLayoutEffect(() => {
        savedCallback.current = callback
    }, [callback])


    useEffect(() => {
        if (!delay && delay !== 0) {
            return;
        }
        
        if (paused) {
            console.log('paused', paused, 'stop', stop)
            if (stop) {
                clearInterval(timer.current);
                startTime.current = 0;
                consumeTime.current = 0;
                return;
            }
            clearInterval(timer.current);
            consumeTime.current = consumeTime.current + Date.now() - startTime.current;
        } else if (consumeTime.current) {
            console.log('consume', consumeTime.current, stop)
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
            if (stop) {
                setStop(false);
            }
            timer.current = window.setInterval(() => {
                startTime.current = Date.now();
                savedCallback.current();
            }, delay);
            startTime.current = Date.now();
        }
        return () => {
            clearInterval(timer.current)
        };
    }, [delay, paused, stop]);


    return {
        pause: () => {
            setPause(true);
        },
        resume: () => {
            setPause(false);
        },
        paused,
        stop: () => {
            setStop(true);
        },
        start: () => {
            setStop(false);
        }
    }
}