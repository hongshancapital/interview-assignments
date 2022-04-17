import { useState, useEffect } from 'react';

export function useCarouselIndex(count: number, duration: number): number {
    const [index, setIndex] = useState(-1);

    useEffect(() => {
        const timer = setInterval(() => {
            setIndex((index + 1) % count);
        }, index < 0 ? 0 : duration);
        return () => {
            clearInterval(timer);
        };
    }, [count, duration, index]);

    return index;
}