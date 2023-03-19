import React from 'react';

const CarouselDot = ({ active, selected, onDotClick }: {
  active?: boolean;
  selected?: boolean;
  onDotClick: () => void;
}) => {

  const classNames = ['carousel-dot'];
  if (active) classNames.push('carousel-dot-active');
  if (selected) classNames.push('carousel-dot-selected');
  
  return (
    <div className={classNames.join(' ')} onClick={onDotClick}>
      <div></div>
    </div>
  )
}

if (process.env.NODE_ENV !== 'production') {
  CarouselDot.displayName = 'CarouselDot';
}

export default React.memo(CarouselDot);
