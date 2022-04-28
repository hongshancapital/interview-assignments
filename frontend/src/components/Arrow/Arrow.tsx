import React, { FC, memo } from "react";
import { CarouselArrowProps, SlideDirection } from '../../types/carousel';
import './Arrow.css';

const CarouselArrow: FC<CarouselArrowProps> = memo((arrowProps) => {
    const { showArrow, onSlideToDirection } = arrowProps;

    if (!showArrow) {
      return null;
    }

    return <>
      <p
        className="carousel-arrow carousel-prev"
        onClick={() => onSlideToDirection(SlideDirection.Left)}>PREV</p>
      <p
        className="carousel-arrow carousel-next"
        onClick={() => onSlideToDirection(SlideDirection.Right)}>NEXT</p>
    </>
})

export default CarouselArrow;