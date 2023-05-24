import React, { Children, useEffect, useMemo, useState } from "react";
import CarouselProgress from "./CarouselProgress";
import "./Carousel.css";

const transitionTime = 300; // ms
const smooth = `transform ${transitionTime}ms ease`;

interface CarouselProps {
  children: React.ReactNode;
  interval: number; // ms
}

function next(total: number, current: number) {
  return (current + 1) % total;
}

export default function Carousel(props: CarouselProps) {
  const { children, interval } = props;
  const [current, setCurrent] = useState(0);

  const slideCount = Children.count(children);
  const slides = useMemo(() => {
    return Children.map(children, (child) => {
      return <div className={"carousel__slide"}>{child}</div>;
    });
  }, [children]);
  const slideStyle = {
    width: `${100 * slideCount}%`,
    transform: `translateX(${(-current / slideCount) * 100}%)`,
    transition: smooth,
  };

  useEffect(() => {
    const timer = setInterval(() => {
      setCurrent(next(slideCount, current));
    }, interval);

    return () => clearInterval(timer);
  });

  return (
    <>
      {slideCount > 0 && (
        <div className="carousel">
          <div className="carousel__slides" style={slideStyle}>
            {slides}
          </div>
          <div className="carousel__progress">
            <CarouselProgress
              currentIndex={current}
              count={slideCount}
              interval={interval}
            ></CarouselProgress>
          </div>
        </div>
      )}
    </>
  );
}
