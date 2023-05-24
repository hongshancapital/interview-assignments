import React from "react";
import "./CarouselProgress.css";

export interface CarouselProgressProps {
  currentIndex: number;
  count: number;
  interval: number;
}

export default function CarouselProgress(props: CarouselProgressProps) {
  const { count, interval, currentIndex } = props;

  return (
    <div
      className="progress"
      style={{ "--duration": `${interval}ms` } as React.CSSProperties}
    >
      {Array.from({ length: count }, (_, index) => {
        return (
          <div
            key={index}
            className={`progress__item ${
              index === currentIndex ? "active" : ""
            }`}
          ></div>
        );
      })}
    </div>
  );
}
