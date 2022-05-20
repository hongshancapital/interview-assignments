import React, { Children, FC, useState } from "react";
import { CarouselConfigEnum, CarouseProps } from "./type";
import Dots from "./dots";
import "./index.css";
import CarouselItem from "./carousel-item";

const Carousel: FC<CarouseProps> = ({
  interval = CarouselConfigEnum.interval,
  speed = CarouselConfigEnum.speed,
  dots = true,
  autoPlay = true,
  className,
  children,
}) => {
  const [activeIndex, setActiveIndex] = useState(0);
  const childrenCount = Children.count(children);
  const style = {
    width: `${100 * childrenCount}%`,
    transform: `translateX(-${100 * activeIndex}vw)`,
    willChange: "transform",
    transition: `all ${speed}ms linear`,
  };

  const switchTo = (index: number) => {
    if (activeIndex !== index) {
      setActiveIndex(index);
    }
  };

  return (
    <div className={`carousel-slider ${className ?? ""}`}>
      <div className="carousel-slider-container" style={style}>
        {Children.map(children, (component) => {
          if (!React.isValidElement(component)) {
            throw new Error("Carousel children is invalid element.");
          }
          if (Children.count(children) <= 1) {
            throw new Error(
              "CarouselItem length cannot less than or equal to 1."
            );
          }
          if (
            // @ts-ignore
            component.type.displayName !== CarouselItem.displayName
          ) {
            throw new Error(
              `Carousel children must be ${CarouselItem.displayName}`
            );
          }
          return React.cloneElement(component, component.props);
        })}
      </div>
      {dots && childrenCount && (
        <Dots
          interval={interval}
          autoPlay={autoPlay}
          count={childrenCount}
          activeIndex={activeIndex}
          switchTo={switchTo}
        />
      )}
    </div>
  );
};

export default Carousel;
