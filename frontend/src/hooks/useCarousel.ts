import { useEffect, useState, useRef } from "react";
const DEFAULT_ACTIVE_INDEX = 0;
interface IState {
    activeIndex: number;
    intervalId: number;
}

export function useCarousel(count: number, duration: number) {
    const [activeIndex, setActiveIndex] = useState<number>(DEFAULT_ACTIVE_INDEX);
    const state = useRef<IState>({ activeIndex: DEFAULT_ACTIVE_INDEX, intervalId: DEFAULT_ACTIVE_INDEX })

    const cancelInterval = () => {
        if (state.current.intervalId) {
            window.clearInterval(state.current.intervalId)
        }
    }

    const startInterval = () => {
        cancelInterval()
        state.current.intervalId = window.setInterval(() => {
            const temp = (state.current.activeIndex + 1) % count;
            setActiveIndex(temp);
            state.current.activeIndex = temp;
        }, duration);
        return cancelInterval;
    }

    useEffect(startInterval, [count, duration]);

    const handleClick = (index: number) => {
        setActiveIndex(index)
        state.current.activeIndex = index;
        startInterval()
    }
    return {
        activeIndex,
        handleClick,
    }
}