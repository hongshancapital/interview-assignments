import React, { FC, useEffect, useState } from "react";
import { CarouselConfigEnum, DotsProps } from "./type";

const Dots: FC<DotsProps> = ({
  className = "",
  autoPlay = true,
  activeIndex: defaultActiveIndex = 0,
  interval = CarouselConfigEnum.interval,
  count = 0,
  switchTo,
}) => {
  const [activeIndex, setActiveIndex] = useState(defaultActiveIndex);
  const [animationPaused, setAnimationPaused] = useState(false);

  const handleClick = (index: number) => {
    setActiveIndex(index);
  };

  const handleMouseEnter = (index: number) => {
    if (index === activeIndex) {
      setAnimationPaused(true);
    }
  };

  const handleMouseLeave = (index: number) => {
    if (index === activeIndex) {
      setAnimationPaused(false);
    }
  };

  const handleAnimationEnd = () => {
    let nextIndex = activeIndex + 1;
    if (nextIndex === count) {
      nextIndex = 0;
    }
    setActiveIndex(nextIndex);
  };

  useEffect(() => {
    switchTo?.(activeIndex);
  }, [activeIndex, switchTo]);

  const progressAnimationState = `slide-animation-${animationPaused ? "paused" : "running"}-state`;

  return (
    <ul className={`carousel-dots ${className}`}>
      {Array.from(Array(count).keys()).map((index) => (
        <li
          key={index}
          onClick={() => handleClick(index)}
          onMouseEnter={() => handleMouseEnter(index)}
          onMouseLeave={() => handleMouseLeave(index)}
        >
          {autoPlay && (
            <div
              className={(activeIndex === index ? `carousel-dots-progress ` : "") + progressAnimationState}
              style={{
                animationDuration: `${interval}ms`
              }}
              onAnimationEnd={handleAnimationEnd}
            />
          )}
        </li>
      ))}
    </ul>
  );
};

export default Dots;
