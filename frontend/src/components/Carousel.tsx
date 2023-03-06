import React, { useState, useEffect, ReactNode } from "react";
import "./carousel.css";

interface Props {
  interval?: number;
  children: ReactNode[];
}

const DEFAULT_INTERVAL = 3000
const HUNDRED = 100
const THOUSAND = 1000

const Carousel: React.FC<Props> = ({ interval = DEFAULT_INTERVAL, children }) => {
  const [currentIndex, setCurrentIndex] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setCurrentIndex(prevIndex =>
        prevIndex === children.length - 1 ? 0 : prevIndex + 1
      );
    }, interval);

    return () => clearInterval(timer);
  }, [interval, children.length]);

  return (
    <div className="scdt-carousel">
      <div
        className="scdt-carousel__track"
        style={{
          width: `${children.length * HUNDRED}%`,
          left: `${-currentIndex * HUNDRED}%`,
        }}
      >
        {React.Children.map(children, (child, i) => (
          <div
            className="scdt-carousel__item-container"
            key={i}
            style={{ width: `${HUNDRED / children.length}%` }}
          >
            {child}
          </div>
        ))}
      </div>
      <div className="scdt-carousel__indicators-container">
        {React.Children.map(children, (_, i) => (
          <div
            className={`scdt-carousel__indicator${
              i === currentIndex ? " active" : ""
            }`}
            key={i}
          >
            <div
              className="scdt-carousel__indicator-animation"
              style={{ animationDuration: `${interval / THOUSAND}s` }}
            ></div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default Carousel;
