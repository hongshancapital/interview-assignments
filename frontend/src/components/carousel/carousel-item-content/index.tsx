import type { ReactNode, FC } from "react";
import { CommonProps } from "../types/common-types";
import classnames from "classnames";

export interface CarouselItemContentProps extends CommonProps {
  imgUrl: string;
  title?: ReactNode;
  subTitle?: ReactNode;
  imgSize?: number | string;
}

const CarouselItemContent: FC<CarouselItemContentProps> = ({
  imgUrl,
  title,
  subTitle,
  style,
  className,
  imgSize = 300,
}) => {
  return (
    <div
      className={classnames("carousel-item-wrap", className)}
      style={{ ...style, height: imgSize }}
    >
      <div className="carousel-item-content">
        <div className="carousel-item-title">{title}</div>
        <div className="carousel-item-subtitle">{subTitle}</div>
      </div>

      <img
        src={imgUrl}
        alt=""
        className="carousel-item-bg"
        style={{ width: imgSize, height: imgSize }}
      />
    </div>
  );
};

CarouselItemContent.displayName = "CarouselItemContent";

export default CarouselItemContent;
