import { Children, ReactNode, useCallback, useEffect, useRef, useState } from "react";
import './index.css';

interface CarouselProps {
    children?: ReactNode
    duration?: number
    speed?: number
}

interface CarouselItemProps {
    bg?: string
    children?: ReactNode
}

function Carousel({
    children,
    duration = 2500,
    speed = 500
}: CarouselProps) {
    const [activeIndex, setActiveIndex] = useState(0);
    const count = Children.count(children);
    const timer = useRef<number>();


    const moveTo = (i: number) => {
        setActiveIndex(i);
        move();
    }
    
    // 滑动
    const move = useCallback(() => {
        clearTimeout(timer.current)
        timer.current = window.setTimeout(() => {
            setActiveIndex((index) => (index + 1) % count)
            move();
        }, duration);
    }, [duration, count])

    useEffect(() => {
        move()
        return () => {
            clearTimeout(timer.current);
            timer.current = undefined;
        }
    }, [move])

    return (
        <div className="carousel-container">
            <div className="carousel-list" style={{
                width: `${count * 100}vw`,
                transform: `translateX(-${activeIndex * 100}vw)`,
                transition: `transform ${speed / 1000}s linear`
            }}>
                {children}
            </div>
            <div className="carousel-progress">
                {new Array(count).fill(undefined).map((v, i) => (
                    <div className="progress-item" key={i} onClick={() => {
                        moveTo(i)
                    }}>
                        {activeIndex === i && <div className="active-progress" style={{animation: `fill ${duration / 1000}s linear`}}></div>}
                    </div>
                ))}
            </div>
        </div>
    )
}

function Item({
    bg = '',
    children
}: CarouselItemProps) {
    return (
        <div className={`carousel-item`} style={{backgroundColor: bg}}>{children}</div>
    )
}

Carousel.Item = Item;

export default Carousel;