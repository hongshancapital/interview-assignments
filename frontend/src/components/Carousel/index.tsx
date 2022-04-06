import React from 'react'
import { useState, useEffect } from 'react';

import CarouselCaption from './CarouselCaption';
import CarouselItem from './CarouselItem';
import CarouselNav from './CarouselNav';

import './Carousel.d.ts';
import './Carousel.css';

const Carousel: React.FC<CarouselProps> = ({ interval = 3000, children }) => {
  const [activeIndex, setActiveIndex] = useState(0)
  const numberOfChildren = children.length
  const [left, setLeft] = useState('0px') 

  useEffect(() => {
      const timer = window.setInterval(
        () => {
          const nextActiveIndex = (activeIndex + 1) % numberOfChildren
          setActiveIndex(nextActiveIndex);
          setLeft(`-${nextActiveIndex * 100}vw`)
        },
        interval
      );
      return () => clearInterval(timer);
    },
    [activeIndex, numberOfChildren, interval]
  );

  return (
    <div className="carousel">
      <div className="carousel__container" style={{ left }}>
        { children }
      </div>
      <div className="carousel__nav__container">
        {
          new Array(numberOfChildren).fill(1).map((item, index) =>
            <CarouselNav key={index} active={index === activeIndex} />
          )
        }
      </div>
    </div>
  );
};

export default Object.assign(Carousel, {
  Caption: CarouselCaption,
  Item: CarouselItem
});