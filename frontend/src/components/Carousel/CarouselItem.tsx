import React, { memo } from "react";
import type { CarouselItemPropsType } from "../../types/CarouselType";

const CarouselItem = ({ title, describe, image }: CarouselItemPropsType) => {
  return (
    <div
      className="carousel_item"
      style={{
        backgroundImage: `url(${image})`,
      }}
    >
      <div className="carousel_info">
        <h1>{title}</h1>
        <span>{describe}</span>
      </div>
    </div>
  );
};

CarouselItem.displayName = "CarouselItem";

export default memo(CarouselItem);
