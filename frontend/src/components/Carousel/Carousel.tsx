import React, { useMemo, useState } from "react";
import "./Carousel.css";
import Indicators from "./Indicators";

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

const Carousel = (props: CarouselProps) => {
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

  const [activeIndex, setActiveIndex] = useState(() => {
    return defaultActiveIndex < 0
      ? 0
      : defaultActiveIndex > childrenCount - 1
      ? childrenCount - 1
      : defaultActiveIndex;
  });

  const sliderStyle = {
    transition: `transform ${duration}ms`,
    transform: `translateX(-${100 * activeIndex}%)`,
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
        activeIndex={activeIndex}
        goto={setActiveIndex}
      />
    </div>
  );
};

export default Carousel;
