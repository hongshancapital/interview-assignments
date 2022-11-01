import React, { useEffect } from "react";
import classnames from "classnames";
import "./style/index.css";
import { useCarousel } from "./hooks";

interface CarouselProps {
  duration?: number;
  style?: React.CSSProperties;
}

const Carousel: React.FC<CarouselProps> = ({
  duration = 3000,
  children,
  style = {},
}) => {
  const childrenLength = React.Children.count(children);

  const { containerRef, containerWidth, slider, handleDotClick } = useCarousel(
    childrenLength,
    duration
  );

  return (
    <div className="carousel-container" ref={containerRef} style={style}>
      <div className="carousel-list">
        {/*轮播的元素*/}
        <div
          className="carousel-track"
          style={{
            width: childrenLength * containerWidth,
            transform: `translateX(${-slider * containerWidth}px)`,
          }}
        >
          {React.Children.map(children, (child, index) => {
            return React.cloneElement(child as React.ReactElement, {
              key: index,
              className: slider === index ? "ele_active" : "",
              style: {
                width: containerWidth,
                ...((child as React.ReactElement).props.style || {}),
              },
            });
          })}
        </div>
      </div>
      {/*轮播下方的dot*/}
      <div className="carousel-indicator">
        {Array.from({ length: childrenLength }).map((_, index) => (
          <div
            className="indicator"
            key={index}
            onClick={() => handleDotClick(index)}
          >
            <span
              className={classnames(
                "progress",
                slider === index ? "animate" : "",
                slider === index ? "active" : ""
              )}
            />
          </div>
        ))}
      </div>
    </div>
  );
};

export default Carousel;
