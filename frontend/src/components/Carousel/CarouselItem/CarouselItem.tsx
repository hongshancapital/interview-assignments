import React, { FC } from "react";

import "./CarouselItem.css";

export interface CarouselItemProps {}

const CarouselItem: FC<CarouselItemProps> = ({ children }) => {
    return <div className="carousel-item">{children}</div>;
};

export default CarouselItem;
