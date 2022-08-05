import React, { FC, memo } from "react";
import { CarouselItemProps } from '../../types/carousel';
import "./Items.css";

const CarouselItems: FC<CarouselItemProps> = memo((itemProps) => {
    const { children, className = '', currentStateIndex, duration } = itemProps;
    const childrens = Array.isArray(children) ? children : children ? [children] : [];
    const len = childrens.length;

    if (!len) {
      return null;
    }

    return <ul
      className="carousel-wrap"
      style={{
        transform: `translateX(-${currentStateIndex}00%)`,
        transitionDuration: `${duration}ms`,
      }}
    >
      {childrens.map((item, index) => <li key={`carouselKey-${index}`} className={`carousel-item ${className}`}>{item}</li>)}
    </ul>
})

export default CarouselItems;