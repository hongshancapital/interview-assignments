import { useCallback, useState, useRef, useEffect } from 'react'

export interface SwiperOption {
    autoplay?:boolean,
    delay?:number
}

export type SwiperTrigger = (index:number) => void

export default function useSwiper(count:number, option:SwiperOption): [index:number, go:SwiperTrigger] {
    const {
        autoplay = true,
        delay = 3
    } = option
    const [index, setIndex] = useState(0)
    const timerRef = useRef<NodeJS.Timer|null>(null)
    const clearTimer = () => timerRef.current && window.clearInterval(timerRef.current)
    const getNextIndex = useCallback((index:number) => {
        if (count <= 0) return 0
        index++
        if (index >= count) {
            index = 0
        }
        return index
    }, [count])

    const run = useCallback(() => {
        timerRef.current = setInterval(() => {
            setIndex(getNextIndex)
        }, delay * 1000)
    }, [delay, getNextIndex])

    const go:SwiperTrigger = (activeIndex:number) => {
        setIndex(activeIndex)
        clearTimer()
        run()
    }

    useEffect(() => {
        if (autoplay) {
            run()
        }
        return () => {
            clearTimer()
        }
    }, [run, autoplay])

    return [index, go]
}
