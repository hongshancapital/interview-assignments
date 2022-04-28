import React, { FC, memo } from "react";
import { CarouselItemProps } from '../../types/carousel';
import "./Items.css";

const CarouselItems: FC<CarouselItemProps> = memo((itemProps) => {
    const { children, className = '', carouselRef } = itemProps;
    const childrens = Array.isArray(children) ? children : children ? [children] : [];
    const len = childrens.length;

    if (!len) {
      return null;
    }

    return <ul ref={carouselRef} className="carousel-wrap">
      {len > 1 && <li
        className={`carousel-item last-carousel-item ${className}`}
      >{childrens[len - 1]}</li>}

      {childrens.map((item, index) => <li key={`carouselKey-${index}`} className={`carousel-item ${className}`}>{item}</li>)}
      
      {len > 1 && <li
        className={`carousel-item first-carousel-item ${className}`}
      >{childrens[0]}</li>}
    </ul>
})

export default CarouselItems;