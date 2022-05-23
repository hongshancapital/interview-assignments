import React, { useState, useEffect, useImperativeHandle } from "react";
import "./index.css";
import { CarouselRef } from "../Carousel";

type CarouselIndicatorsProps = {
  ref: React.ForwardedRef<CarouselRef>;
  autoPlay?: boolean;
  activeIndex?: number;
  count: number;
  interval?: number;
  goTo: (index: number) => void;
};

const Indicators = React.forwardRef<CarouselRef, CarouselIndicatorsProps>((props, ref) => {
  const { count, activeIndex: defaultActiveIndex = 0, autoPlay, interval, goTo } = props;
  const [activeIndex, setActiveIndex] = useState(defaultActiveIndex);
  const [animationPaused, setAnimationPaused] = useState(false);

  const handleOnclick = (index: number) => setActiveIndex(index);
  const handleMouseEnter = (index: number) => index === activeIndex && setAnimationPaused(true);
  const handleMouseLeave = (index: number) => index === activeIndex && setAnimationPaused(false);
  const handleAnimationEnd = () => setActiveIndex((activeIndex + 1) % count);

  useEffect(() => {
    goTo?.(activeIndex);
  }, [activeIndex, goTo]);

  useImperativeHandle(ref, () => ({
    goTo: setActiveIndex,
    next: () => setActiveIndex((activeIndex + 1) % count),
    prev: () => setActiveIndex(activeIndex - 1 < 0 ? count - 1 : activeIndex - 1),
    current: activeIndex
  }));

  return (
    <div className="carousel-indicators">
      {new Array(count).fill(true).map((_, index) => {
        return (
          <div
            key={index}
            onClick={() => handleOnclick(index)}
            onMouseEnter={() => handleMouseEnter(index)}
            onMouseLeave={() => handleMouseLeave(index)}
            className={
              activeIndex === index
                ? "carousel-indicator carousel-indicator-active"
                : "carousel-indicator"
            }
          >
            {autoPlay ? (
              <div
                className="indicator-progress"
                style={{ 
                  animationDuration: `${interval}ms`,
                  animationPlayState: animationPaused ? "paused" : "running",
               }}
                onAnimationEnd={handleAnimationEnd}
              ></div>
            ) : (
              <div className="indicator-content"></div>
            )}
          </div>
        );
      })}
    </div>
  );
});

export default Indicators;
