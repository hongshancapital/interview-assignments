import { forwardRef, useRef, useImperativeHandle } from "react";
import type { ReactNode } from "react";
import { CommonProps, Size } from "../types/common-types";
import classnames from "classnames";

export interface CarouselItemContentProps extends CommonProps {
  imgUrl: string;
  imgSize?: Size;
  title?: ReactNode;
  subTitle?: ReactNode;
}

export interface CarouselItemContentRef {
  getContainerSize: () => Size;
}

const CarouselItemContent = forwardRef<
  CarouselItemContentRef,
  CarouselItemContentProps
>(({ imgUrl, title, subTitle, imgSize, style, className }, ref) => {
  const containerRef = useRef<HTMLDivElement>(null);

  const getContainerSize = () => {
    if (containerRef.current) {
      return {
        width: containerRef.current?.getBoundingClientRect().width,
        height: containerRef.current?.getBoundingClientRect().height,
      };
    }

    return {
      width: 0,
      height: 0,
    };
  };

  useImperativeHandle(ref, () => {
    return {
      getContainerSize,
    };
  });

  return (
    <div
      className={classnames("carousel-item-wrap", className)}
      ref={containerRef}
      style={style}
    >
      <div className="carousel-item-content">
        <div className="carousel-item-title">{title}</div>
        <div className="carousel-item-subtitle">{subTitle}</div>
      </div>

      <img
        src={imgUrl}
        alt=""
        className="carousel-item-bg"
        style={{ ...imgSize }}
      />
    </div>
  );
});

CarouselItemContent.displayName = "CarouselItemContent";

export default CarouselItemContent;
