import React from "react";
import { CasrouselBarProps } from "./Casrousel.types";
import "./styles/CasrouselBar.css";
const CarouselBar: React.FC<CasrouselBarProps> = (props) => {
  const { activeIndex, barLen, wait, selectIndex, autoConfig } = props;
  return (
    <div className="bar-container">
      {new Array(barLen).fill(0).map((item, index) => (
        <div
          className="carousel-bar-container"
          key={index}
          onClick={() => {
            selectIndex(index);
          }}
        >
          <div className="carousel-bar">
            {activeIndex === index ? (
              <div
                className={`${
                  autoConfig
                    ? "carousel-bar-progress-auto"
                    : "carousel-bar-progress"
                }`}
                style={{ animationDuration: `${wait / 1000}s` }}
              />
            ) : null}
          </div>
        </div>
      ))}
    </div>
  );
};

export default CarouselBar;
