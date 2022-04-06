import { useState, useEffect } from 'react';
import { range } from 'lodash';

import CarouselCaption from './CarouselCaption';
import CarouselItem from './CarouselItem';

import './Carousel.css'

const CarouselNav = ({ interval = 3000, active, style }) => {
  return (
    <div className="carousel__nav" style={style}>
      <div className={ active && 'carousel__nav--active' }></div>
    </div>
  )
}

const Carousel = ({ children }) => {
  const [activeIndex, setActiveIndex] = useState(0)
  const numberOfChildren = children.length
  const [left, setLeft] = useState(0) 

  useEffect(() => {
      const timer = window.setInterval(
        () => {
          const nextActiveIndex = (activeIndex + 1) % numberOfChildren
          setActiveIndex(nextActiveIndex);
          setLeft(`-${nextActiveIndex * 100}vw`)
        },
        3000
      );
      return () => clearInterval(timer);
    },
    [activeIndex, numberOfChildren]
  );

  const imageContainerSytle = {
    position: 'absolute',
    display: 'flex',
    left,
    top: 0
  }

  const navContainerStyle = {
    position: 'absolute',
    bottom: '5vh',
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'space-between',
    left: 'auto'
  }

  return (
    <div className="carousel">
      <div className="carousel__container" style={imageContainerSytle}>
        { children }
      </div>
      <div style={navContainerStyle}>
        {
          range(0, numberOfChildren).map(index =>
            <CarouselNav key={index} active={index === activeIndex} />
          )
        }
      </div>
    </div>
  );
};

Carousel.Item = CarouselItem;
Carousel.Caption = CarouselCaption;
export default Carousel;