import React, { FC, useState, useMemo, useCallback, useEffect, useRef } from 'react';
import { ICarouselProps, ICarouselItem } from '../../interface/carousel';
import CarouselItems from '../CarouselItems';
import Dots from '../Dots';
import './index.scss';

const Carousel: FC<ICarouselProps> = props => {
  const { list, delay = 3 } = props;
  const [currentIndex, setCurrentIndex] = useState(0);
  let timer = useRef<ReturnType<typeof setInterval> | null>(null);

  const listLength = useMemo(() => {
    return list.length;
  }, [list]);

  const animateStyle = useMemo(() => {
    return {
      width: `${listLength * 100}vw`,
      transform: `translateX(-${currentIndex * 100}vw)`,
      transitionDuration: '0.3s',
    };
  }, [currentIndex, listLength]);

  const handleNext = useCallback(() => {
    setCurrentIndex(index => (index === listLength - 1 ? 0 : index + 1));
  }, [listLength]);

  useEffect(() => {
    timer.current = setInterval(() => {
      handleNext();
    }, delay * 1000);
    return () => {
      timer.current && clearInterval(timer.current);
    };
  }, [handleNext, delay]);

  const handleStop = () => {
    timer.current && clearInterval(timer.current);
  };

  const handleStart = () => {
    timer.current && clearInterval(timer.current);
    timer.current = setInterval(() => {
      handleNext();
    }, delay * 1000);
  };

  return (
    <div
      className="carousel"
      onMouseEnter={handleStop}
      onMouseOut={handleStart}
      onClick={handleNext}
    >
      <div className="carousel__container" style={animateStyle}>
        {list.map((item: ICarouselItem, index: number) => (
          <CarouselItems key={index} {...item} />
        ))}
      </div>
      <div className="carousel__dots">
        <Dots activeIndex={currentIndex} count={listLength} delay={delay} />
      </div>
    </div>
  );
};

export default Carousel;
