import React, { FC } from "react";
import { CarouselItemProps } from "./type";

const CarouselItem: FC<CarouselItemProps> = ({ className = "", children }) => (
    <div className={`carousel-item ${className}`}>
        {children}
    </div>
);

export default CarouselItem;
