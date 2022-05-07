import React, { FC, useEffect, useState } from "react";
import "./index.css";

const defaultDuration = 3000;

export interface ICarouselItem {
  titles?: string[];
  texts?: string[];
  color?: string;
  backgroundImageURL?: string;
}

interface ICarousel
  extends FC<{
    items: ICarouselItem[];
    duration?: number;
  }> {
  Item: FC;
}

const Carousel: ICarousel = function ({ items, duration: durationProp }) {
  const [currentIndex, setCurrentIndex] = useState(0);
  let itemNum = items.length;
  let duration = defaultDuration;

  if (durationProp !== undefined) {
    duration = durationProp;
  }

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentIndex((index) => {
        return (index + 1) % itemNum;
      });
    }, duration);
    return () => {
      clearInterval(interval);
    };
  }, [itemNum, duration]);

  return (
    <div
      className="carousel-container"
      data-testid="carousel"
      data-test-current-index={currentIndex}
    >
      <div
        className="carousel"
        style={{
          transform: `translateX(${-currentIndex * 100}%)`,
        }}
      >
        {items.map((item, i) => {
          const style: { backgroundImage?: string; color?: string } = {};

          if (item.backgroundImageURL) {
            style.backgroundImage = `url(${item.backgroundImageURL})`;
          }

          if (item.color) {
            style.color = item.color;
          }

          return (
            <Carousel.Item key={i}>
              <div
                className="carousel-item"
                style={style}
                data-testid="carousel-inner-item"
              >
                {item?.titles?.map?.((title) => {
                  return (
                    <h1 key={title} className="carousel-item__title">
                      {title}
                    </h1>
                  );
                })}
                {item?.texts?.map?.((text) => {
                  return (
                    <p key={text} className="carousel-item__text">
                      {text}
                    </p>
                  );
                })}
              </div>
            </Carousel.Item>
          );
        })}
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
            >
              <div
                className="carousel-progress-bar-list__item__process"
                style={{
                  animationDuration: `${duration}ms`,
                }}
              ></div>
            </li>
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
