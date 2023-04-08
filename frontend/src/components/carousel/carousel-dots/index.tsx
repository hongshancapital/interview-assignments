import type { FC } from "react";
import { useRef } from "react";
import type { CarouselProps } from "..";
import type { CommonProps } from "../types/common-types";

interface CarouselDotsProps
  extends Pick<CarouselProps, "autoplayInterval">,
    CommonProps {
  count: number;
  activeIdx: number;
}

const CarouselDots: FC<CarouselDotsProps> = ({
  count,
  activeIdx,
  autoplayInterval,
  style,
}) => {
  const wrapRef = useRef<HTMLDivElement>(null);
  const outerWidth = wrapRef.current?.getBoundingClientRect().width || 0;

  if (count <= 1) {
    return null;
  }

  return (
    <div className={"carousel-dots"} style={style}>
      {new Array(count).fill(0).map((_, idx) => {
        const isActive = activeIdx === idx;
        return (
          <div className={"carousel-dot"} key={idx} ref={wrapRef}>
            {
              <div
                className="carousel-dot-progress"
                style={{
                  transition: `width ${isActive ? autoplayInterval : 0}ms`,
                  width: isActive ? `${outerWidth}px` : 0,
                }}
              />
            }
          </div>
        );
      })}
    </div>
  );
};

export default CarouselDots;
