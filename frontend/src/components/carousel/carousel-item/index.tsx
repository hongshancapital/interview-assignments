import { forwardRef, useRef, useImperativeHandle, useState } from "react";
import type { PropsWithChildren, Ref } from "react";
import { CommonProps } from "../types/common-types";

export interface CarouselItemRef {
  setOffset: React.Dispatch<React.SetStateAction<number>>;
}

export type CarouselItemProps = PropsWithChildren<
  CommonProps & { ref?: Ref<CarouselItemRef> }
>;

const CarouselItem = forwardRef<CarouselItemRef, CarouselItemProps>(
  (props, ref) => {
    const { children, style } = props;
    const [offset, setOffset] = useState(0);
    const swipeItemRef = useRef<HTMLDivElement>(null);

    useImperativeHandle(ref, () => {
      return {
        setOffset,
      };
    });

    return (
      <div
        ref={swipeItemRef}
        className="carousel-item"
        style={{ transform: offset ? `translateX(${offset}px)` : "", ...style }}
      >
        {children}
      </div>
    );
  }
);

CarouselItem.displayName = "CarouselItem";

export default CarouselItem;
