import React from 'react'

const CarouselItem: React.FC<CarouselItemProps> = ({ children, bgColor }) => {
  return (
    <div className="carousel__item" style={{ backgroundColor: bgColor }}>
      { children }
    </div>
  )
}

export default CarouselItem