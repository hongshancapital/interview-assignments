import React, { FC, HTMLAttributes } from "react";
import { classnames } from "../utils/classnames";
import "./index.css";

export interface ICarouselItemProps {}

export const CarouselItem: FC<ICarouselItemProps & HTMLAttributes<HTMLDivElement>> = ({
  children,
  className,
  ...divProps
}) => {
  return (
    <div className={classnames("carousel-item", className)} {...divProps}>
      {children}
    </div>
  );
};
