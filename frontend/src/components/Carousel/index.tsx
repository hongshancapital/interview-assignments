import React, { useState, useMemo, ReactNode, ReactElement } from 'react';

import './index.scss'

interface defaultProps {
    children: ReactNode | Array<ReactNode>,
    key?: string | number
}

interface ICarouselProps extends defaultProps {
    intervalTime?: number,
}

interface ICarousel {
    (props: ICarouselProps): ReactElement,
    CarouselItem(props: defaultProps): ReactElement,
    timer?: NodeJS.Timer
}

const Carousel: ICarousel = function (props: ICarouselProps) {
    const {
        intervalTime = 2000,
        children
    } = props;

    const [currentCarouselItemIndex, setCurrentCarouselItemIndex] = useState(0);
    const [dotPersent, setDotPersent] = useState(0);
    const childrenCount = React.Children.count(children);

    Carousel.timer = useMemo(
        () => {
            clearInterval(Carousel.timer);
            let dotStartTime: number = Date.now();
            return setInterval(() => {
                let persent: string = ((Date.now() - dotStartTime) / intervalTime).toFixed(4);
                setDotPersent(Number(persent) * 100);
            }, 16)
        },
        [currentCarouselItemIndex] // 只有在切换轮播卡片后，才会开始新的interval，来处理dot进度条的变化
    );
    useMemo(
        () => {
            setTimeout(() => {
                setCurrentCarouselItemIndex(currentCarouselItemIndex >= childrenCount - 1 ? 0 : currentCarouselItemIndex + 1);
                setDotPersent(0);
            }, intervalTime)
        },
        [currentCarouselItemIndex, childrenCount] // 在轮播卡片切换后，会开启interval延时的定时器，会切换下一张轮播卡片
    )

    return (
        <div className="container">
            <div className="carousel-container" style={{transform: `translateX(${-currentCarouselItemIndex * 80}vw)`}}>
                {children}
            </div>
            <div className="dot-container">
                {React.Children.map(children, (item, index) => {
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

export default Carousel;