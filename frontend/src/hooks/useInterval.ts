import { useEffect, useRef } from "react";
import { isNull } from "../components/Carousel/util";

export enum TimerDelayEnum {
    INFINITE = 'INFINITE'
}

export default function useInterval(callback: () => void, delay: number | TimerDelayEnum.INFINITE) {
    const savedCallbackRef = useRef(callback)
    const timerRef = useRef<null | NodeJS.Timer>(null)
    const clearTimerRef = useRef<null | (() => void)>(null)
    const createTimerRef = useRef<null | (() => void)>(null)

    const clearTimer = () => {
        if (!isNull(timerRef.current)) {
            clearInterval(timerRef.current)
            timerRef.current = null
        }
    }

    const createTimer = () => {
        clearTimer();
        if (delay === TimerDelayEnum.INFINITE) {
            return
        }
        timerRef.current = setInterval(() => {
            savedCallbackRef.current()
        }, delay)
    }

    useEffect(() => {
        savedCallbackRef.current = callback
        clearTimerRef.current = clearTimer
        createTimerRef.current = createTimer
    })

    useEffect(() => {
        createTimerRef.current?.()

        return () => {
            clearTimerRef.current?.()
        }
    }, [delay])

    return { clearTimer, createTimer }
}
