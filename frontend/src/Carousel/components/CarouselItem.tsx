import React from 'react';

const CarouselItem: React.FC<{ width: number; children: React.ReactNode }> = ({ width, children }) => {
  return <div style={{ width }} className="carousel-item">{children}</div>
}

if (process.env.NODE_ENV !== 'production') {
  CarouselItem.displayName = 'CarouselItem';
}

export default React.memo(CarouselItem);