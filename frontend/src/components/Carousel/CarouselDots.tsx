import React from 'react';
import './CarouselDots.css';

const CarouselDots: React.FC<CarouselDotsProps> = ({ itemCount, duration, onIndex, onAnimationEnd }) => {
  const style = {
    animationDuration: duration / 1000 + 's'
  };
  return (
    <div className="carousel-dots">
      { new Array(itemCount).fill(null).map((v, i) => 
        <div className={i === onIndex ? 'carousel-dots-item carousel-dots-item-on' : 'carousel-dots-item'}key={i}>
          <div style={style} onAnimationEnd={onAnimationEnd}></div>
        </div>
      )}
    </div>
  )
}
export default CarouselDots;