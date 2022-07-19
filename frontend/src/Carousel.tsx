import React, { useEffect, useRef, useState } from "react";
import "./carousel.css";

export interface CarouselProps {
  children?: React.ReactNode;
  stayTime?: number;
}

const Carousel = ({ stayTime = 5000, children }: CarouselProps) => {
  const [precent, setPrecent] = useState(0);
  const [pos, setPos] = useState(0);
  const timer = useRef<null | number>(null);
  const [width, setWidth] = useState(window.innerWidth);
  const len = React.Children.count(children);

  useEffect(() => {
    if (timer.current) {
      clearInterval(timer.current);
    }
    const step = 2;
    const stepNum = 100 / step;
    timer.current = window.setInterval(
      () => setPrecent((p) => (p < 100 ? p + step : 0)),
      stayTime / stepNum
    );
    return () => {
      if (timer.current) {
        clearInterval(timer.current);
      }
    };
  }, [stayTime]);

  useEffect(() => {
    if (precent === 100) {
      setPrecent(0);
      setPos((i) => (i + 1 >= len ? 0 : i + 1));
    }
  }, [precent, len]);

  useEffect(() => {
    const handler = () => {
      setWidth(window.innerWidth);
    };
    window.addEventListener("resize", handler);

    return () => {
      window.removeEventListener("resize", handler);
    };
  }, []);

  return (
    <div className="carousel">
      <div
        className="carousel-wrapper"
        style={{
          transform: `translate3d(${-pos * width}px, 0px, 0px)`,
        }}
      >
        {React.Children.map(children, (child, i) => {
          if (React.isValidElement(child)) {
            const { className } = child.props;
            return React.cloneElement(child as React.ReactElement, {
              className: `carousel-item ${className}`,
            });
          }
        })}
      </div>
      <ul className="dot">
        {React.Children.map(children, (child, i) => (
          <li>
            <div
              className="process"
              style={
                i === pos
                  ? { transform: `translateX(${precent - 100}%)` }
                  : { display: "none" }
              }
            />
            {i}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default Carousel;
