import React, { FC, CSSProperties } from "react";
import classNames from "classnames";
import './index.css';

import { ICarouselItemProps } from "./type";

const CarouselItem: FC<{ item: ICarouselItemProps, isActive: boolean, width: number }> = ({ item, isActive, width }) => {
  const itemStyle: CSSProperties = {
    backgroundImage: `url(${item.image})`,
    backgroundColor: `${item.bgColor}`,
    color: item.color,
    width: `${width}px`
  }
  return (
    <div
      className={classNames('carousel-item', { 'carousel-item-active': isActive })}
      style={itemStyle}
    >
      {
        item.title.map((title, index) => (
          <div
            className="carousel-title"
            key={`carousel-title-${index}`}
          >
            {title}
          </div>
        ))
      }
      {
        item.content ? item.content.map((content, index) => (
          <div
            className="carousel-content"
            key={`carousel-content-${index}`}
          >
            {content}
          </div>
        )) : null
      }
    </div>
  );
};

export default CarouselItem;