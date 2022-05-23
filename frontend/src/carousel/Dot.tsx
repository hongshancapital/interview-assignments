import React from 'react';
import type { CarouselDotProps } from './types';

const Dot = ({ isActive, duration }: CarouselDotProps) => {
  const innerProps = isActive ? {
    className: 'carousel-dot-active',
    style: { animationDuration: `${duration}ms` },
  } : {};
  
  return (
    <div className="carousel-dot">
      <div {...innerProps} />
    </div>
  )
}

export default Dot;
