import React from 'react';
import './CarouselDots.css';

const CarouselDots: React.FC<CarouselDotsProps> = ({ itemCount, duration, onIndex }) => {
  const style = {
    animationDuration: duration / 1000 + 's'
  };
  const dots = [];
  for (let i = 0; i < itemCount; i++) {
    const className = i === onIndex ? 'carousel-dots-item carousel-dots-item-on' : 'carousel-dots-item';
    dots.push(<div className={className} key={i}><div style={style}></div></div>);
  }
  return (
    <div className="carousel-dots">
      {dots}
    </div>
  )
}
export default CarouselDots;