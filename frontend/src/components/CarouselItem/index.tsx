import React, { FC } from "react";
import './index.scss';

export interface CarouselItemProps {
  title: string;
  subTitle?: string;
  className?: string;
}

const CarouselItem: FC<CarouselItemProps> = ({
  title,
  subTitle,
  className = '',
}) => {
  return (
    <div className={`carousel-item ${className}`}>
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