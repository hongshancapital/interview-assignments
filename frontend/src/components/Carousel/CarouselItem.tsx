import React, { ReactNode } from "react";

export interface ICarouselItemProps {
  active: boolean;
  children: ReactNode;
}

function CarouselItem(props: ICarouselItemProps) {
  const { children, active } = props;
  return (
    <div
      className={`carousel-slides__item ${
        active ? "carousel-slides__item--active" : ""
      }`}
    >
      {children}
    </div>
  );
}

export default CarouselItem;
