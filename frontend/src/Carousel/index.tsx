import React, { useState, useEffect, useMemo, useCallback } from 'react';
import { CarouselItem } from './components/CarouselItem/index';
import { IndicatorLines } from './components/IndicatorLines/index';
import './index.css';

export interface ICarouselItemProps {
    color: string
    title: string[],
    imageName: string,
    subTitle?: string[],
}

interface IProps {
    list: ICarouselItemProps[],
    duration: number,
}

export const Carousel = ({
    list,
    duration
}: IProps) => {
    const [currentIndex, setCurrentIndex] = useState(0);

    const swapImage = useCallback(() => {
        setCurrentIndex(pre => (pre + 1) % list.length);
    }, [list.length]);

    useEffect(() => {
        let timer: any = null;
        timer = setInterval(swapImage, duration);

        return () => {
            clearInterval(timer);
            timer = null;
        }
    }, [duration, swapImage]);

    const imgBoxStyle = useMemo(() => {
        return {
            width: `${list.length * 100}vw`,
            transform: `translateX(-${currentIndex * 100}vw)`,
        }
    }, [list, currentIndex]);

    return  (
        <div className='carousel-box'>
            <div className='img-box' style={imgBoxStyle}>
                {
                    list.map((item: ICarouselItemProps) => {
                        return <CarouselItem
                            key={item.imageName}
                            {...item}
                        />
                    })
                }
            </div>
            <div className='indicator-box'>
                {
                    list.map((item: ICarouselItemProps, index: number) => {
                        return <IndicatorLines
                            key={index}
                            active={currentIndex === index}
                            duration={duration}
                        />
                    })
                }
            </div>
        </div>
    );
};