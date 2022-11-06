import React, { useState } from "react";
import "./index.css";

type CarouselIndicatorsProps = {
  autoPlay?: boolean;
  activeIndex: number;
  count: number;
  interval: number;
  goto: (index: number) => void;
};

const Indicators = (props: CarouselIndicatorsProps) => {
  const { count, activeIndex, autoPlay, interval, goto } = props;
  const [animationPaused, setAnimationPaused] = useState(false);

  const handleOnclick = (index: number) => goto(index);
  const handleMouseEnter = (index: number) => index === activeIndex && setAnimationPaused(true);
  const handleMouseLeave = (index: number) => index === activeIndex && setAnimationPaused(false);
  const handleAnimationEnd = () => goto((activeIndex + 1) % count);

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
};

export default Indicators;
