import React, { FC } from "react";
import "./CarouselIndicator.css";

type IndicatorProps = {
  count: number;
  current: number;
  duration: number;
  onIndexChange(i: number): void;
};

const CarouselIndicator: FC<IndicatorProps> = ({
  count,
  current,
  duration,
  onIndexChange,
}) => {
  const renderIndicator = () => {
    let indicators = [];
    for (let i = 0; i < count; i++) {
      indicators.push(
        <li
          className="carousel__indicator__item"
          key={`carousel__indicator__${i}`}
          onClick={() => {
            onIndexChange(i);
          }}
        >
          <span
            className={`carousel__indicator__item__fill ${
              current > i ? "actived" : ""
            }`}
            style={
              current === i
                ? {
                    animation: `slideRight ${duration / 1000}s forwards`,
                  }
                : {}
            }
          ></span>
        </li>
      );
    }
    return indicators;
  };

  return <ul className="carousel__indicator">{renderIndicator()}</ul>;
};

export default CarouselIndicator;
