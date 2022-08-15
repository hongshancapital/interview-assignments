import React, { FC } from "react";

import "./CarouselItem.css";

export interface CarouselItemProps {
  bgColor: string;
  bgSrc: string;
  title?: string;
  titleColor?: string;
  description?: string;
  descriptionColor?: string;
}

const CarouselItem: FC<CarouselItemProps> = ({
  bgColor,
  bgSrc,
  title,
  titleColor,
  description,
  descriptionColor,
}) => {
  return (
    <div className={"carousel-item"} style={{ backgroundColor: bgColor }}>
      <img className="carousel-item-bg" src={bgSrc} />
      <div className="carousel-item-content">
        {!!title && (
          <div className="carousel-item-title" style={{ color: titleColor }}>
            {title}
          </div>
        )}
        {!!description && (
          <div
            className="carousel-item-description"
            style={{ color: descriptionColor }}
          >
            {description}
          </div>
        )}
      </div>
    </div>
  );
};

export default CarouselItem;
