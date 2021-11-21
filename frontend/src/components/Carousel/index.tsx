import React, { useState, useImperativeHandle } from "react";
import "./index.css";
import Slider, { CommonProps } from "./Slider";

export interface CarouselProps extends CommonProps {
  children?: React.ReactNode;
}

export interface CarouselHandle {
  go: (index: number) => void; // 跳转到指定条目
  next: () => void; // 下一条
  prev: () => void; // 上一条
}

const Carousel: React.ForwardRefRenderFunction<CarouselHandle, CarouselProps> =
  ({ children, ...otherProps }, ref) => {
    const childrenCount = React.Children.count(children);
    const [currentIndex, setCurrentIndex] = useState<number>(0);

    useImperativeHandle(ref, () => ({
      go: (index: number) => {
        if (index === currentIndex) return;
        if (typeof index !== "number" || index < 0 || index >= childrenCount) {
          return console.warn("索引无效，请检查后再调用");
        }
        setCurrentIndex(index);
      },
      next: () => {
        let nextIndex = currentIndex + 1;
        nextIndex = nextIndex >= childrenCount ? 0 : nextIndex;
        setCurrentIndex(nextIndex);
      },
      prev: () => {
        let prevIndex = currentIndex - 1;
        prevIndex = prevIndex < 0 ? childrenCount - 1 : prevIndex;
        setCurrentIndex(prevIndex);
      },
    }));

    return (
      <div className="container">
        <div
          className="wrap"
          style={{ transform: `translateX(${currentIndex * -100}%)` }}
        >
          {React.Children.map(children, (child) => (
            <div className="carousel">{child}</div>
          ))}
        </div>
        <Slider
          {...otherProps}
          setCurrentIndex={setCurrentIndex}
          currentIndex={currentIndex}
          itemNum={childrenCount}
        />
      </div>
    );
  };

export default React.forwardRef(Carousel);
