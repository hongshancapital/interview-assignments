import React from "react";
import "./Carousel.css";
import { useSlider } from "./hooks";
type CarouselProps = {
  interval: number;
  transitionDuration: number;
  children: React.ReactNode[];
};
const Carousel: React.FC<CarouselProps> = ({
  interval,
  transitionDuration,
  children,
}) => {
  const count = React.Children.count(children);
  const index = useSlider(count, interval);
  return (
    <div className="wrapper">
      <div
        className="list-wrapper"
        style={{
          width: `${100 * count}%`,
          transform: `translateX(-${(100 * index) / count}%)`,
          transition: `transform ${transitionDuration}ms ease`,
        }}
      >
        {children.map((child, idx) => {
          return (
            <div className="list-item" key={idx}>
              {child}
            </div>
          );
        })}
      </div>

      <div className="dots-wrapper">
        {children.map((child, idx) => {
          return (
            <div className="dot-outer" key={idx}>
              {
                <div
                  className="dot-inner"
                  style={
                    idx === index
                      ? {
                          animation: `dot-active ${interval}ms linear`,
                        }
                      : {}
                  }
                ></div>
              }
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default Carousel;
