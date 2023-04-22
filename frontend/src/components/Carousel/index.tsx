import React, { useState, useEffect } from 'react';
import CarouselItem from '../CarouselItem';
import CarouselTab from '../CarouselTab';
import { CarouselItemInfo } from '../../types';

import './styles.scss';

export interface CarouselProps {
  items: CarouselItemInfo[];
  duration?: number;
  speed?: number;
}

const Carousel = (props: CarouselProps) => {
  const { items = [], duration = 3000, speed = 400 } = props;

  const [activeIndex, setActiveIndex] = useState(0);
  const timerRef = React.useRef<NodeJS.Timeout>();

  const handleTabClick = (index: number) => {
    // only changes the active index if there are more than 1 items
    if (items.length > 1) {
      setActiveIndex(index);
    }
  }

  const clearTimer = () => {
    if (timerRef.current) {
      clearTimeout(timerRef.current);
    }
  }

  useEffect(() => {
    clearTimer();

    // only starts the timer if there are more than 1 items
    if (items.length > 1) {
      timerRef.current = setTimeout(() => {
        const nextIndex = (activeIndex + 1) % items.length;
        setActiveIndex(nextIndex);
      }, duration);
    }

    return () => clearTimer();
  }, [activeIndex, items.length, duration]);

  return (
    <div className="carousel">
      <div
        className="carousel-items"
        style={{
          width: `${100 * items.length}vw`,
          transform: `translateX(-${100 * activeIndex}vw)`,
          transition: `transform ${speed}ms linear`,
        }}
      >
        {items.map((item, index) => (
          <CarouselItem key={index} {...item} />
        ))}
      </div>
      <div className="carousel-tabs">
        {items.map((item, index) => (
          <CarouselTab
            key={index}
            index={index}
            onChange={handleTabClick}
            isActive={items.length > 1 && index === activeIndex}
            duration={duration}
          />
        ))}
      </div>
    </div>
  )
}

export default Carousel;
