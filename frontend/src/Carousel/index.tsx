import React, { useState, useEffect, useCallback } from 'react';
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
    let timer: NodeJS.Timer | null = null;
    timer = setInterval(swapImage, duration);

    return () => {
      if (timer) {
        clearInterval(timer);
      }
      timer = null;
    }
  }, [duration, swapImage]);

  return (
    <div className='carousel-box'>
      <div className={'img-box '}
        ref={(node) => {
          if (node) {
            node.style.setProperty('transform', `translateX(-${currentIndex * 100}%)`);
          }
        }}>
        {
          list.map((item: ICarouselItemProps, index: number) => {
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