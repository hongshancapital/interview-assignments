import React, { useState, useEffect, useRef } from 'react';
import { CarouselProps } from "./interface";
import { DEFAULT_SLIDE_INTERVAL, DEFAULT_INDEX } from './constants';
import CarouselItem from './CarouselItem';
import CarouselIndicator from './CarouselIndicator';

import './index.scss';

const Carousel: React.FC<CarouselProps> = (props: CarouselProps) => {
  const {
    items = [],
    slideInterval = DEFAULT_SLIDE_INTERVAL,
    autoPlay = true,
  } = props || {};
  const [activeIndex, setActiveIndex] = useState(DEFAULT_INDEX);
  const timer = useRef<NodeJS.Timer>();

  const handleIndicatorClick = (index: number) => {
    setActiveIndex(index);
  }

  useEffect(() => {
    if (!autoPlay) return;
    timer.current = setInterval(() => {
      setActiveIndex((index) => (index + 1) % items.length)
    }, slideInterval);

    return () => {
      timer.current && clearInterval(timer.current);
    };
  }, [autoPlay, items.length, slideInterval]);

  return (
    <div className="carousel-wrap">
      <div
        className="carousel-content"
        style={{ transform: `translateX(${-activeIndex * 100}%)` }}
      >
        {items.map((config, idx) => (
          <CarouselItem {...config} key={`carousel_item_${idx}`} />
        ))}
      </div>
      <CarouselIndicator
        count={items.length}
        active={activeIndex}
        slideInterval={slideInterval}
        handleClick={handleIndicatorClick}
      />
    </div>
  );
};

export default Carousel;