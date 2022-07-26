import React from "react";
import './style/index.scss'

export interface CarouselIndicatorsProps {
  activeIndex: number,
  count: number,
  animationDuration: number,
}

const Indicators = (props: CarouselIndicatorsProps) => { 

  const { activeIndex, count, animationDuration } = props;
  if (count <= 1) {
    return null;
  }
  return (
    <div className="carousel-indicators__wrap">
      {(new Array(count)).fill('').map((_, index) => (
        <div key={index} className="carousel-indicator">
          <div
            className={index === activeIndex ? 'carousel-indicator__progress' : ''}
            style={{ animationDuration: `${animationDuration}ms` }}
          />
        </div>
      ))}
    </div>
  )
}

export default Indicators;