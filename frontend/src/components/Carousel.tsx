import React, { FC, useState, useEffect, useRef } from 'react'
import './Carousel.css';

export interface CarouselProps {
  defaultActive?: number;
  interval?: number;
  children: any[];
}

export const Carousel: FC<CarouselProps> = ({children, defaultActive = 1, interval}) => {
  
  const dotWapperWidth = 60;
  const dotWidthInterval = interval ? interval / dotWapperWidth : 50;

  const SCREEN_WIDTH = window.screen.width;
  const [active, setActive] = useState(defaultActive);
  
  const defaultTransform = 'translate3d(0px, 0, 0)';
  const [transform, setTransform] = useState(defaultTransform);
  const setTransition = (currentctive: number) => {
    const distance = (1 - currentctive) * SCREEN_WIDTH;
    setTransform(`translate3d(${distance}px, 0, 0)`);
  }

  const defaultDotWidth = 0;
  const [dotWidth, setDotWidth] = useState(defaultDotWidth);

  const handlePrev = () => {
    const currentActive = active === 1 ? children.length : active - 1;
    setActive(currentActive);
    setTransition(currentActive);
  }
  
  const handleNext = () => {
    const currentActive = active === children.length ? 1 : active + 1;
    setActive(currentActive);
    setTransition(currentActive);
  }

  useEffect(() => {
    let slickTimer = 0;
    slickTimer = setInterval(() => {
      handleNext();
    }, interval);
    let dotWidth = 0;
    const timer = setInterval(() => {
      dotWidth++;
      if (dotWidth === dotWapperWidth) {
        clearInterval(timer);
      }
      setDotWidth(dotWidth);
    }, dotWidthInterval);
    return () => {
      clearInterval(slickTimer);
    };
  }, [active]);

  return (
    <div className="carousel">
      <div className="container" style={{ transform }}>
        {
          children.map((child, index) => (
            <div key={index} style={{left: index * SCREEN_WIDTH}} className="item">
              {child}
            </div>
          ))
        }
      </div>
      <ul className="slick">
        {
          children.map((_, index) => (
            <li key={index} className="slick-dots">
              {active === index+1 && <p style={{width: dotWidth}}></p>}
            </li>
          ))
        }
      </ul>
    </div>
  )

}

Carousel.defaultProps = {
  interval: 3000
}

export default Carousel;