import React, { ReactNode } from 'react';
import { useCarousel } from '../../hooks/useCarousel';
import Indicator from './Indicator';
import './Carousel.css';

export interface CarouselProps {
  speed?: number;
  duration?: number;
  children?: ReactNode;
}

const Carousel = ({
  speed = 1000,
  duration = 3000,
  children,
}: CarouselProps) => {
  const total = React.Children.count(children);
  const { currentIndex } = useCarousel({ duration, total });

  return (
    <div className={'carousel'}>
      <ul
        className={'carousel-item__container'}
        style={{
          transform: `translateX(-${currentIndex * 100}%)`,
          transitionDuration: `${Math.min(speed, duration)}ms`,
        }}
      >
        {React.Children.map(children, (child, index) => {
          return (
            <li key={index} className={'carousel-item'}>
              {child}
            </li>
          );
        })}
      </ul>
      <div className={'indicator__container'}>
        <Indicator
          currentIndex={currentIndex}
          total={total}
          duration={duration}
        />
      </div>
    </div>
  );
};
Carousel.displayName = 'Carousel';

export default Carousel;
