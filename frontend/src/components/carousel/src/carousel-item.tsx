import React, { FC } from "react";
import { CarouselItemProps } from "./type";

const CarouselItem: FC<CarouselItemProps> = ({ className = "", ...props }) => {
  const { children, style } = props;
  return (
    <div className={`slick-slide ` + className} style={style && style}>
      {children}
    </div>
  );
};

export default CarouselItem;
