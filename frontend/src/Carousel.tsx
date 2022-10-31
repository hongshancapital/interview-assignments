
import React, { useState } from "react";
import './Carousel.css';
import useInterval from "./useInterval";

interface CarouselProps {
    interval?: number;
    children: JSX.Element[];
}

const Carousel = (props: CarouselProps) => {
    const {interval = 3000, children} = props
    const [currentIndex, setCurrentIndex] = useState(0);
    const count = children.length;

    const {pause, resume, paused, stop} = useInterval(() => {
        if (currentIndex === count - 1) setCurrentIndex(0);
        else setCurrentIndex(currentIndex + 1);
    }, interval);

    const changeIndex = (index: number) => {
        if (index === currentIndex) return;
        stop();
        setCurrentIndex(index);
    }

    return (
        <div className="carousel" onMouseMove={pause} onMouseLeave={resume}>
            <div
                className="carousel__wrapper"
                style={{transform: `translate3D(-${currentIndex * 100}%, 0, 0)`}}
            >
                {children.map((comp, idx) => (
                    <div className="carousel__page" key={idx}>{comp}</div>
                ))}
            </div>
            <div className="carousel__dots">
                {children.map((_, index) => (
                    <CarouselDot
                        key={index}
                        duration={interval / 1000}
                        active={index === currentIndex}
                        pause={paused}
                        onClick={() => changeIndex(index)}
                    />
                ))}
            </div>
        </div>
    )
}

interface CarouselDotProps {
    duration: number;
    active: boolean;
    pause: boolean;
    onClick?: () => void;
}

function CarouselDot(props: CarouselDotProps) {
    const {duration, active, pause = false, onClick} = props

    let dotClassName = 'carousel-dot';
    if (active) dotClassName += ' carousel-dot__active';
    if (pause) dotClassName += ' carousel-dot__pause';

    

    return (
        <div onClick={onClick} className="carousel-dot__wrapper">
            <div className={dotClassName} >
                <div className="carousel-dot__progress" style={{'animationDuration': `${duration}s`}}></div>
            </div>
        </div>
    )
}

export default Carousel;