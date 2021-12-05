import React, { FC } from "react";

import "./CarouselItem.css";

export interface CarouselItemProps {
    width?: string | number;
    height?: string | number;
}

const CarouselItem: FC<CarouselItemProps> = ({ width, height, children }) => {
    return (
        <div
            className="carousel-item"
            style={{
                width: width ? width : "100%",
                height: height ? height : "100%",
            }}
        >
            {children}
        </div>
    );
};

export default CarouselItem;
