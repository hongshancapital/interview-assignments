import React, { FC } from "react";
import './index.scss';

export interface CarouselItemProps {
  title: string;
  subTitle?: string;
  style?: React.CSSProperties; 
}

const CarouselItem: FC<CarouselItemProps> = ({
  title,
  subTitle,
  style = {},
}) => {
  return (
    <div className="carousel-item" style={style}>
      <div className="content">
        <h1>{title}</h1>
        {
          subTitle && <p className="sub-title">{subTitle}</p>
        }
      </div>
    </div>
  );
};

export default CarouselItem;