import React, { useState, useEffect } from 'react';
import Dot from './Dot';
import Item from './Item';
import type { CarouselProps, CarouselItemProps, useAnimateProps } from './types';
import './index.css';

export type { CarouselItemProps };

const INTERVAL = 1000;
const ANIMATIONSPEED = 300;

export const useAnimate = ({ count, interval }: useAnimateProps) => {
  const [ activeIndex, setActiveIndex ] = useState<number>(0);

  useEffect(() => {
    if (count <= 1) {
      return () => {};
    }
    let timer: number;
    const change = () => {
      timer = window.setTimeout(() => {
        setActiveIndex(val => val >= count - 1 ? 0 : val + 1);
        change();
      }, interval);
    }
    change();
    return () => {
      window.clearTimeout(timer);
    }
  }, [ count, interval ]);

  return { activeIndex }
}

const Carousel = ({ items, interval = INTERVAL, animationSpeed = ANIMATIONSPEED }: CarouselProps) => {
  const { activeIndex } = useAnimate({ count: items.length, interval});
  const contentStyle = { 
    transition: `transform ${animationSpeed}ms`, 
    transform: `translate3D(-${activeIndex * 100}%, 0, 0)` 
  };

  return (
    <div className="carousel">
      <div className="carousel-content" style={contentStyle}>
        {
          items.map((itemProps, i) => (
            <Item key={i} {...itemProps} />
          ))
        }
      </div>
      <div className="carousel-dots">
        {
          items.map((itemProps, i) => (
            <Dot key={i} isActive={activeIndex === i} duration={interval} />
          ))
        }
      </div>
    </div>
  )
}

export default Carousel;
