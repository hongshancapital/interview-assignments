import React, { FC } from "react";
import './style/index.scss';

export interface CarouselItemProps {
  children: React.ReactNode
}

const CarouselItem: FC<CarouselItemProps> = (props: CarouselItemProps) => {
  const { children } = props;
  return (
    <div className="carousel__item">
      {children}
    </div>
  )
}

export default CarouselItem;