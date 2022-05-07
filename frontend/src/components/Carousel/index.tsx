import React from 'react'
import { useState } from 'react';

import useInterval from '../../hooks/useInterval'
import CarouselCaption from './CarouselCaption';
import CarouselItem from './CarouselItem';
import CarouselNav from './CarouselNav';
import './Carousel.d.ts';
import './Carousel.css';

const Carousel: React.FC<CarouselProps> = ({ interval = 3000, children }) => {
  const [activeIndex, setActiveIndex] = useState(0)
  const numberOfChildren = children.length

  useInterval(() => {
    const nextActiveIndex = (activeIndex + 1) % numberOfChildren
    setActiveIndex(nextActiveIndex);
  }, interval)

  return (
    <div className="carousel">
      <div className="carousel__container" style={{ left: `-${activeIndex * 100}vw` }}>
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