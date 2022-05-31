import React, { useMemo, useImperativeHandle } from "react";
import "./Carousel.css";
import Indicators from "./Indicators";
import useCarousel from "./useCarousel";

export type CarouselRef = {
  goto: (index: number) => void;
  next: () => void;
  prev: () => void;
  current: number;
};

type CarouselProps = {
  /* 是否自动切换 */
  autoPlay?: boolean;
  /* 切换的间隔时间（毫秒）  */
  interval?: number;
  /* 动画过渡时间（毫秒） */
  duration?: number;
  /* 默认选中的轮播图索引 */
  defaultActiveIndex?: number;
  children: React.ReactNode;
};

const Carousel = React.forwardRef<CarouselRef, CarouselProps>((props, ref) => {
  const {
    children,
    autoPlay = false,
    interval = 3000,
    duration = 500,
    defaultActiveIndex = 0,
  } = props;

  const childrenCount = useMemo(
    () => React.Children.count(children),
    [children]
  );

  const { goto, prev, next, current } = useCarousel({ count: childrenCount, defaultActiveIndex });

   useImperativeHandle(ref, () => ({
    goto,
    next,
    prev,
    current
  }));

  const sliderStyle = {
    transition: `transform ${duration}ms`,
    transform: `translateX(-${100 * current}%)`,
  };

  return (
    <div className="carousel-container">
      <div className="carousel-slider-wrapper">
        <ul className="carousel-slider" style={sliderStyle}>
          {React.Children.map(children, (component, index) => {
            if (!React.isValidElement(component)) {
              throw new Error("Carousel children is invalid element.");
            }
            return (
              <li className="carousel-slide" key={`carousel-slide${index}`}>
                {React.cloneElement(component, component.props)}
              </li>
            );
          })}
        </ul>
      </div>
      <Indicators
        interval={interval}
        autoPlay={autoPlay}
        count={childrenCount}
        activeIndex={current}
        goto={goto}
      />
    </div>
  );
});

export default Carousel;
