import React, {
  useRef,
  HTMLAttributes,
  useLayoutEffect,
  useState,
  useCallback,
  useMemo,
  ReactChild,
  ReactFragment,
  ReactPortal,
  CSSProperties,
} from "react";
import Dot, { DotStatus } from "./Dot";
import "./index.css";

interface CarouselProps extends HTMLAttributes<HTMLDivElement> {
  cycleTime?: number;
}

/* 大致取 1/6 ，可根据实际情况调优 */
export function getDotWidth({
  unitWidth,
  dotCount,
}: {
  unitWidth: number;
  dotCount: number;
}) {
  return Math.floor(unitWidth / 6 / dotCount);
}

const Carousel = (props: CarouselProps) => {
  const { children, cycleTime = 3000 } = props;

  const [unitWidth, setUnitWidth] = useState<number>(0);
  const [left, setLeft] = useState<number>(0);
  const [current, setCurrent] = useState<number>(0);

  const innerRef = useRef<HTMLDivElement>(null);

  const _children = useMemo<
    Array<ReactChild | ReactFragment | ReactPortal>
  >(() => {
    if (children === null || children === undefined) {
      return [];
    }
    if (Array.isArray(children)) {
      return children;
    }
    return [children];
  }, [children]);

  const count = _children.length;

  useLayoutEffect(() => {
    const containerWidth = innerRef?.current?.clientWidth || 0;
    setUnitWidth(containerWidth);
  }, [innerRef]);

  const handleDotTimeEnd = useCallback(
    (index: number) => {
      const nextIndex = (index + 1) % count;
      setLeft(nextIndex * unitWidth);
      setCurrent(nextIndex);
    },
    [count, unitWidth]
  );

  const innerContainerStyle: CSSProperties =
    unitWidth > 0
      ? {
          width: count * unitWidth,
          left: -left,
        }
      : {};

  const childStyle: CSSProperties =
    unitWidth > 0
      ? {
          width: unitWidth,
        }
      : {};

  const dotWidth = getDotWidth({
    unitWidth,
    dotCount: count,
  });

  const getDotStatus: (index: number) => DotStatus = (index: number) => {
    if (index === current) return "loading";
    return "sleep";
  };

  return (
    <div className="carousel" ref={innerRef} {...props}>
      <div className="carousel-inner" style={innerContainerStyle}>
        {_children?.map((child, key) => (
          <div
            className="carousel-child"
            style={childStyle}
            key={key}
            data-index={key}
          >
            {child}
          </div>
        ))}
      </div>
      <div className="carousel-dots">
        {unitWidth > 0 &&
          _children.map((_, index) => {
            return (
              <Dot
                dotWidth={dotWidth}
                key={index}
                cycleTime={cycleTime}
                status={getDotStatus(index)}
                index={index}
                onTimeEnd={handleDotTimeEnd}
              />
            );
          })}
      </div>
    </div>
  );
};

export default Carousel;
