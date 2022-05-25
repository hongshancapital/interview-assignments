import React, { useState, useMemo, ReactNode } from 'react';

import './index.scss'

interface defaultProps {
    children: ReactNode | Array<ReactNode>,
    key?: string | number
}

interface ICarouselProps extends defaultProps {
    intervalTime?: number,
}

let timer: NodeJS.Timer;
export default function Carousel(props: ICarouselProps) {
    const {
        intervalTime = 2000,
        children
    } = props;

    const [currentCarouselItemIndex, setCurrentCarouselItemIndex] = useState(0);
    const [dotPersent, setDotPersent] = useState(0)

    useMemo(
        () => {
            clearInterval(timer);
            let dotStartTime: number = Date.now();
            timer = setInterval(() => {
                let persent: string = ((Date.now() - dotStartTime) / intervalTime).toFixed(2);
                setDotPersent(Number(persent) * 100);
            }, 60)
        },
        [currentCarouselItemIndex]
    );
    useMemo(
        () => {
            setTimeout(() => {
                setCurrentCarouselItemIndex(currentCarouselItemIndex >= 2 ? 0 : currentCarouselItemIndex + 1);
                setDotPersent(0);
            }, intervalTime)
        },
        [currentCarouselItemIndex]
    )

    return (
        <div className="container">
            <div className="carousel-container" style={{transform: `translateX(${-currentCarouselItemIndex * 80}vw)`}}>
                {children}
            </div>
            <div className="dot-container">
                {Array.isArray(children) && children.map((item, index) => {
                    return <div className="dot-item" key={index} style={currentCarouselItemIndex === index ? {background: `linear-gradient(to right, white 0%, white ${dotPersent}%,  gray ${dotPersent}%, gray 100%)`} : {}}></div>
                })}
            </div>
        </div>
    )
}

Carousel.CarouselItem = function(props: defaultProps) {
    return (
        <div className="carousel-item">{props.children}</div>
    )
} 