import React, { FC, useEffect, useState } from "react";
import "./index.css";

const duration = 3000;

interface ICarousel extends FC {
  Item: FC;
}

const Carousel: ICarousel = function ({ children }) {
  const [currentIndex, setCurrentIndex] = useState(0);
  let itemNum = React.Children.count(children);

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentIndex((index) => {
        return (index + 1) % itemNum;
      });
    }, duration);
    return () => {
      clearInterval(interval);
    };
  }, [itemNum]);

  return (
    <div className="carousel-container" data-testid="carousel">
      <div
        className="carousel"
        style={{
          transform: `translateX(${-currentIndex * 100}%)`,
        }}
      >
        {children}
      </div>

      <ul className="carousel-progress-bar-list">
        {new Array(itemNum).fill(0).map((_, i) => {
          return (
            <li
              data-testid="carousel-progress-bar-item"
              key={i}
              className={`carousel-progress-bar-list__item ${
                currentIndex === i
                  ? "carousel-progress-bar-list__item--active"
                  : ""
              }`}
            ></li>
          );
        })}
      </ul>
    </div>
  );
};

const CarouselItem: FC = function ({ children }) {
  return (
    <div className="carousel__item" data-testid="carousel-item">
      {children}
    </div>
  );
};

Carousel.Item = CarouselItem;

export default Carousel;
