import React from 'react';

import './styles.scss';

export interface CarouselTabProps {
  index: number;
  onChange: (index: number) => void;
  isActive: boolean;
  duration: number;
}

const CarouselTab = (props: CarouselTabProps) => {
  const { index, onChange, isActive, duration } = props;

  return (
    <div
      className="carousel-tab"
      onClick={() => onChange(index)}
    >
      {isActive && (
        <div
          className="carousel-tab-progress"
          style={{ animationDuration: `${duration}ms` }}
        />
      )}
    </div>
  )
}

export default CarouselTab;
