import React from 'react';
import { CarouselIndicatorProps } from "./interface";
import { DEFAULT_SLIDE_INTERVAL } from './constants';

import './index.scss';

const CarouselIndicator: React.FC<CarouselIndicatorProps> = (props: CarouselIndicatorProps) => {
  const {
    count = 0,
    active = 0,
    slideInterval = DEFAULT_SLIDE_INTERVAL,
    handleClick,
  } = props;

  return (
    <div className="carousel-indicator">
      {new Array(count).fill(null).map((n, idx) => (
        <div className="item" key={`indicator_item_${idx}`} onClick={()=> handleClick && handleClick(idx)}>
          <div
            className={`indicator ${idx === active ? " active" : ""}`}
            style={{ animationDuration: `${slideInterval}ms` }}
          />
        </div>
      ))}
    </div>
  );
};

export default CarouselIndicator;