import React from 'react';
import './CarouselDots.css';

export interface CarouselDotsProps {
  className?: string;
  style?: React.CSSProperties;
  childrenLength: number;
  duration: number;
  activeIndex?: number;
  onDotClick?: (index: number) => any;
}

export default function CarouselDots({
  className,
  childrenLength,
  duration,
  activeIndex = 0,
  onDotClick,
}: CarouselDotsProps) {
  return (
    <ul className={`carousel-dots ${className}`}>
      {new Array(childrenLength).fill('').map((item, index) =>
        <li className='carousel-dots__item' key={index} onClick={() => onDotClick?.(index)}>
          {index === activeIndex &&
            <div
              className='carousel-dots__item-content'
              style={{animation: `width-change ${duration}ms`}}
            />}
        </li>
      )}
    </ul>
  );
}