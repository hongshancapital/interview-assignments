import React, { useEffect, useState, useRef } from "react";
import { LastSlide } from "./LastSlide";
import { CurrentSlide } from "./CurrentSlide";
import { ItemBars } from "./ItemBars";

interface CarouselProps {
    height?: string;
    width?: string;
    time?: number;
    children: React.ReactNode[];
}

export const Carousel = ({ height, width, time, children }: CarouselProps) => {
    const [currentIndex, setCurrentIndex] = useState<number>(0);
    const [lastIndex, setLastIndex] = useState<number>();
    const initFlag = useRef<boolean>(false);

    useEffect(() => {
        if (!children.length || initFlag.current) {
            return;
        }
        initFlag.current = true;
        setInterval(() => {
            setCurrentIndex(i => {
                setLastIndex(i);
                return (i + 1) % children.length;
            });
        }, time || 3000);
    }, [children.length, time]);

    if (!children.length) {
        return null;
    }

    return (<div style={{ height, width }} role="carousel">
        {lastIndex !== undefined && (<LastSlide>{children[lastIndex]}</LastSlide>)}
        <CurrentSlide hasLastSlide={lastIndex !== undefined} isFirstSlide={currentIndex === 0} time={time || 3000}>{children[currentIndex]}</CurrentSlide>
        <ItemBars itemCount={children.length} time={time || 3000} currentIndex={currentIndex}/>
    </div>)
};