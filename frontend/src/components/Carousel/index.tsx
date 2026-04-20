import CarouselSlide from './components/Slide';
import Pagination from './components/Pagination';
import { useEffect, useRef, useState } from 'react';
import type { PropsWithChildren } from 'react';

import './index.css';

type CarouselProps = PropsWithChildren<{
    count: number;
    duration: number;
    speed: number;
}>;

function Carousel({
    children,
    count,
    speed,
    duration
}: CarouselProps) {
    const [actIdx, setActIdx] = useState(0);
    const timer = useRef<any>();

    function startTimer() {
        timer.current = setTimeout(() => {
            setActIdx((index) => (index + 1 > count - 1 ? 0 : index + 1));
            timer.current = null;
            startTimer();
        }, duration);
    }

    function slideTo(index: number) {
        setActIdx(index);
        clearTimeout(timer.current);
        startTimer();
    }

    useEffect(() => {
        startTimer();
        return () => {
            clearTimeout(timer.current);
        };
    }, []);

    return (
        <div className="carousel-container">
            <div className="carousel-wrapper"
                style={{
                    transition: `transform ${speed}ms linear`,
                    transform: `translate3d(-${100 * actIdx}vw,0,0)
        `}}
            >
                {children}
            </div>
            <Pagination
                slideTo={slideTo}
                pagination={count}
                active={actIdx}
                duration={duration}
            />
        </div>
    );
}

Carousel.Slide = CarouselSlide;

export default  Carousel