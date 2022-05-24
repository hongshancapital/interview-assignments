import React, { useState, useEffect } from 'react';
import Dot from './Dot';
import Item from './Item';
import type { CarouselProps, CarouselItemProps } from './types';
import './index.css';

export type { CarouselItemProps };

const SELECTORPREFIX = 'carousel-animate-next-';
const INTERVAL = 1000;
const ANIMATIONSPEED = 300;

export const useAnimate = ({ count, interval }: { count: number } & Pick<CarouselProps, 'interval'>) => {
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


const useInjectStyles = ({ count, animationSpeed }: { count: number } & Pick<CarouselProps, 'animationSpeed'>) => {
  useEffect(() => {
    const style = document.createElement("style");
    style.appendChild(document.createTextNode(''));
    document.head.appendChild(style);
    Array.from({ length: count }).forEach((item, i) => {
      const rule = `.${SELECTORPREFIX}${i} {
        transition: transform ${animationSpeed}ms;
        transform: translate3D(-${i * 100}%, 0, 0);
      }`;
      style.sheet?.insertRule(rule, 0);
    });
    return () => {
      style.remove();
    }
  }, [animationSpeed, count]);

  return {}
}

const Carousel = ({ items, interval = INTERVAL, animationSpeed = ANIMATIONSPEED }: CarouselProps) => {
  const { activeIndex } = useAnimate({ count: items.length, interval});
  useInjectStyles({count: items.length, animationSpeed});

  return (
    <div className="carousel">
      <div className={`carousel-content ${SELECTORPREFIX}${activeIndex}`}>
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
