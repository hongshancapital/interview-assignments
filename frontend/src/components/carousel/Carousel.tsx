import React from "react";
import { Progress } from "../progress/progress";
import "./carsousel.css";
import { useCarousel } from "./useCarousel";

const transitionTime = 1000;
const interval = 1000;

const smooth = `transform ${transitionTime}ms ease`;

const calculateProgress = (
  index: number,
  current: number,
  progress: number
) => {
  if (index < current) return 100;
  if (index > current) return 0;
  return progress;
};

export const Carousel: React.FC<{}> = ({ children }) => {
  const [current, progress, target] = useCarousel(3, interval, transitionTime);

  const slides = React.Children.toArray(children);

  if (slides.length === 0) {
    throw new Error("Children of Carousel can not be null");
  }
  const length = slides.length;

  const style: React.CSSProperties = {
    transform: "translateX(0)",
    width: `${(length + 2) * 100}%`,
    left: `-${current * 100}%`,
  };

  if (target !== current) {
    const shift = 100 / (length + 2);
    style.transition = smooth;
    style.transform = `translateX(${-shift}%)`;
  }

  return (
    <div className="carousel">
      <div className="carousel-indicators">
        {slides.map((_, index) => (
          <div className="carousel-indicator">
            <Progress
              backgroundColor="gray"
              foregroundColor="white"
              percent={calculateProgress(index, current, progress)}
            />
          </div>
        ))}
      </div>
      <div className="carousel-content" style={style}>
        <div className="carousel-item" key={-1}>
          {slides[slides.length - 1]}
        </div>
        {slides.map((slide, index) => (
          <div className="carousel-item" key={index}>
            {slide}
          </div>
        ))}
        <div className="carousel-item" key={slides.length}>
          {slides[0]}
        </div>
      </div>
    </div>
  );
};
