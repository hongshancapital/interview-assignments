import React from 'react'

const CarouselNav: React.FC<CarouselNavProps> = ({ active }) => {
  return (
    <div className="carousel__nav">
      <div className={ active ? 'carousel__nav--active' : '' }></div>
    </div>
  )
};

export default CarouselNav;