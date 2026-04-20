import React, {
  ReactElement,
  useCallback,
  useEffect,
  useMemo,
  useRef,
  useState,
} from "react";
import "./index.css";
import { ICarouselProps } from "./interface";

const defaultProps = {
  autoPlay: true,
  moveSpeed: 500,
  interval: 3000,
};

export const Carousel: React.FC<ICarouselProps> = (baseProps) => {
  // 合并 props
  const props = { ...defaultProps, ...baseProps };

  // 解构 props
  const { children, moveSpeed, interval } = props;

  // 获取子 React Element 节点
  const childrenList = React.Children.toArray(children).filter((child) =>
    React.isValidElement(child)
  ) as ReactElement[];

  const childrenLength = childrenList.length;

  const [currentIndex, setCurrentIndex] = useState(0);
  const containerRef = useRef<HTMLDivElement>(null);
  const timer = useRef<NodeJS.Timer>();

  const [firstRender, setFirstRender] = useState(true);

  const containerStyle = useMemo(
    () => ({
      transitionDuration: `${moveSpeed}ms`,
      transform: `translateX(${
        -currentIndex * (containerRef.current?.offsetWidth || 0)
      }px)`,
    }),
    [currentIndex, moveSpeed]
  );

  const getClidStyle = useCallback(
    (position: number) => ({
      left: (containerRef.current?.offsetWidth || 0) * position,
    }),
    []
  );

  const getIndicatorActiveStyle = useCallback(
    (position: number) => {
        const active = position === currentIndex && !firstRender;
        return {
            visibility: (active ? "visible" : "hidden") as "visible" | "hidden",
            transitionDuration: `${interval}ms`,
            transform: `scaleX(${active ? "1" : "0"})`,
        };
    },
    [currentIndex, interval, firstRender]
);

  useEffect(() => {
    if (childrenLength <= 1) return;
    timer.current = setInterval(() => {
      setCurrentIndex((prev) => (prev + 1) % childrenLength);
    }, interval);
    return () => {
      clearInterval(timer.current);
    };
  }, [interval, childrenLength]);

  useEffect(() => {
    setFirstRender(false)
  }, [])

  return (
    <div className="carousel-wrap">
      <div
        className="carousel-scroll"
        ref={containerRef}
        data-testid="container"
        style={containerStyle}
      >
        {childrenList.map((child, index) => {
          return React.cloneElement(child, {
            style: getClidStyle(index),
          });
        })}
      </div>
      <div className="indicator-wrap">
        {new Array(childrenLength).fill(0).map((_, index) => (
          <div key={`indicator-${index}`} className="indicator">
            <div
              className="active"
              style={getIndicatorActiveStyle(index)}
            />
          </div>
        ))}
      </div>
    </div>
  );
};
