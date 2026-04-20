import { useCallback, useEffect, useMemo, useRef, useState } from 'react';
import type { CarouselProps } from '.';
import { CarouselDot } from './carousel-dot';
import CarouselItem from './carousel-item';
import './index.scss';

export const Carousel = ({ list, delay: delayProps }: CarouselProps) => {
  const delay = useMemo(() => delayProps || 3000, [delayProps]);
  const [carouselIndex, setCarouselIndex] = useState(0);
  const count = useMemo(() => list.length, [list.length]);
  const getClientWidth = window.document.documentElement.clientWidth;
  const getClientWrapWidth = getClientWidth * count;
  const timer = useRef<number>(0);

  const onDotClick = (index: number) => {
    clearTimer();
    setCarouselIndex(index);
    setTimer();
  };

  const setTimer = useCallback(() => {
    timer.current = window.setTimeout(() => {
      setCarouselIndex((index: number) => (index + 1) % count);
      setTimer();
    }, delay);
  }, [delay, count, setCarouselIndex]);

  const clearTimer = () => {
    clearTimeout(timer.current);
  };

  useEffect(() => {
    setTimer();
    return () => {
      clearTimer();
    };
  }, [setTimer]);
  return (
    <div className="carousel">
      <div className="carousel-list">
        <div
          className="carousel-list-container"
          style={{
            width: getClientWrapWidth,
            transform: `translate3d(-${
              carouselIndex * getClientWidth
            }px, 0px, 0px)`,
          }}
        >
          {list.map((item) => (
            <CarouselItem
              key={item.id}
              {...item}
              width={getClientWidth}
            />
          ))}
        </div>
      </div>
      <div className="carousel-dots">
        {list.map((item, dotIndex) => (
          <CarouselDot
            key={item.id}
            delay={delay}
            active={carouselIndex === dotIndex}
            onDotClick={() => onDotClick(dotIndex)}
          />
        ))}
      </div>
    </div>
  );
};
export default Carousel;
