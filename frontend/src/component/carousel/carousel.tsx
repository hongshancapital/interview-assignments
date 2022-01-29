import React, { useEffect } from 'react';
import { useCarousel } from './use-carousel';
import { CarouselProps } from './index';
import CarouselItem from "./carousel-item";
import Dot from './dot';
import './index.css';

export default function Carousel(props: CarouselProps) {
  const {
    className,
    style,
    duration,
    carouselLength,
    renderChildren,
    current,
    loop,
    clearTimer,
    wrapperRef,
    sizeKey,
    childrenSize,
    carouselAnimateStyle,
    dotPosition,
    dotWrapperStyle,
    dotStyle,
    vertical,
  } = useCarousel(props);

  useEffect(() => {
    loop();
    return clearTimer;
    // eslint-disable-next-line
  }, []);

  return(
    <div data-testid="carousel" className={`carousel${vertical ? ' carousel-vertical' : ''}${className ? ` ${className}` : ''}`} style={style} ref={wrapperRef}>
      <div className="carousel-children" style={{ [sizeKey]: childrenSize, ...carouselAnimateStyle }}>
        {renderChildren()}
      </div>
      <div data-testid="dot" className={`carousel-dot-${dotPosition}`} style={{ ...dotStyle, ...dotWrapperStyle }}>
        <div className="carousel-dot-list">
          {Object.keys(Array.from({ length: carouselLength })).map((item, i) => <Dot key={i} dotPosition={dotPosition} active={current === i} duration={duration} />)}
        </div>
      </div>
    </div>
  )
}

Carousel.Item = CarouselItem
