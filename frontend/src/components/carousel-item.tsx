import React, { FC } from "react";
import { CarouselItemProps } from "./type";

const CarouselItem: FC<CarouselItemProps> = ({ className = "", children }) => (
  <div className={`carousel-item ${className}`}>{children}</div>
);
CarouselItem.displayName = "CarouselItem";
export default CarouselItem;
