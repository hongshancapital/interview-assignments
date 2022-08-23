import React, { FC } from "react";

export interface ICarouselItemProps {}

export const CarouselItem: FC<ICarouselItemProps> = ({ children }) => {
  return <div className="carousel-item">{children}</div>;
};
