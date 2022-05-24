import React, { Children, FC, useEffect, useState, useRef } from 'react';
import { CarouselConfigEnum, CarouseProps } from './type';
import Dots from './dots';
import './index.scss';

const Carousel: FC<CarouseProps> = ({
  interval = CarouselConfigEnum.interval,
  speed = CarouselConfigEnum.speed,
  dots = true,
  autoPlay = true,
  className,
  children,
}) => {
  const [intervalTime, setIntervalTime] = useState<number | undefined>(
    interval
  );
  const [activeIndex, setActiveIndex] = useState(0);
  const childrenCount = Children.count(children);
  const style = {
    width: `${100 * childrenCount}%`,
    transform: `translateX(-${100 * activeIndex}vw)`,
    willChange: 'transform',
    transition: `all ${speed}ms linear`,
  };
  // 清空定时器，重新计时，用于手动切换轮播图
  const resetInterval = () => {
    setIntervalTime(undefined);
    setTimeout(() => {
      setIntervalTime(interval);
    });
  };

  const switchTo = (index: number) => {
    if (activeIndex !== index) {
      setActiveIndex(index);
      resetInterval();
    }
  };

  const useInterval = (fn: () => void, delay?: number) => {
    const fnRef = useRef(fn);
    fnRef.current = fn;
    useEffect(() => {
      if (typeof delay !== 'number' || Number.isNaN(delay) || delay < 0) {
        return;
      }
      let timer = setInterval(fnRef.current, delay);
      return () => clearInterval(timer);
    }, [delay]);
  };

  useInterval(() => {
    if (autoPlay) {
      setActiveIndex((prev) => (prev + 1) % childrenCount);
    }
  }, (autoPlay && intervalTime) || undefined);

  useEffect(() => {
    setActiveIndex(0);
    if (childrenCount <= 1) {
      console.warn('CarouselItem length cannot less than or equal to 1.');
    }
  }, [children, childrenCount]);

  return (
    <div className={`carousel-slider ${className ?? ''}`}>
      <div className='carousel-slider-container' style={style}>
        {children}
      </div>
      {dots && childrenCount && (
        <Dots
          progress={autoPlay}
          count={childrenCount}
          activeIndex={activeIndex}
          switchTo={switchTo}
        />
      )}
    </div>
  );
};

export default Carousel;
