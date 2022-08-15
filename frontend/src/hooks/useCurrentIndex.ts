import { useEffect, useState } from "react";

export interface ICurrentIndexProps {
    total: number;
    duration: number
}

export function useCurrentIndex(props: ICurrentIndexProps) {
    const { total, duration } = props;
    const [index, setIndex] = useState(0);
    useEffect(() => {
        const timer = setInterval(() => {
            let rest = (index + 1) % total;
            setIndex(rest);
        }, duration)
        return () => clearInterval(timer);
    }, [total, duration, index])
    return {
        currentIndex: index
    }
}